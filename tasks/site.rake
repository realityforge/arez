require File.expand_path(File.dirname(__FILE__) + '/util')

SITE_DIR = "#{WORKSPACE_DIR}/reports/site/arez"

desc 'Copy the javadocs to docs dir'
task 'site:javadocs' do
  javadocs_dir = "#{WORKSPACE_DIR}/target/arez/doc"
  file(javadocs_dir).invoke
  mkdir_p SITE_DIR
  cp_r javadocs_dir, "#{SITE_DIR}/api"
end

desc 'Build the website'
task 'site:build' do
  rm_rf SITE_DIR
  mkdir_p File.dirname(SITE_DIR)
  sh "jekyll build --source #{WORKSPACE_DIR}/docs --destination #{SITE_DIR}"
  task('site:javadocs').invoke
end

desc 'Check that the website does not have any broken links'
task 'site:link_check' do
  require 'webrick'
  require 'socket'

  # Get a free port and web address
  socket = Socket.new(:INET, :STREAM, 0)
  socket.bind(Addrinfo.tcp('127.0.0.1', 0))
  address = socket.local_address.ip_address
  port = socket.local_address.ip_port
  socket.close

  webserver = WEBrick::HTTPServer.new(:Port => port, :DocumentRoot => File.dirname(SITE_DIR))
  Thread.new {webserver.start}

  trap('INT') {webserver.shutdown}
  begin
    sh "yarn blc --ordered --recursive --filter-level 3 http://#{address}:#{port}/arez --exclude https://github.com/arez/arez/compare/ --exclude https://github.com/arez/arez.github.io/settings --exclude https://docs.oracle.com/javase/8/docs/api"
  ensure
    webserver.shutdown
  end
end

desc 'Serve the website for developing documentation'
task 'site:serve' do
  mkdir_p File.dirname(SITE_DIR)
  sh "jekyll serve --source #{WORKSPACE_DIR}/docs --destination #{SITE_DIR}"
end

desc 'Build the website'
task 'site:deploy' => ['site:build'] do
  # Verify the site is valid first
  task('site:link_check').invoke

  # Only publish the site off the master branch if running out of Travis
  if ENV['TRAVIS_BRANCH'].nil? || ENV['TRAVIS_BRANCH'] == 'master'
    origin_url = 'https://github.com/arez/arez.github.io.git'

    travis_build_number = ENV['TRAVIS_BUILD_NUMBER']

    in_dir(SITE_DIR) do
      sh 'git init'
      sh 'git add .'
      message =
        travis_build_number.nil? ?
          'Publish website' :
          "Publish website - Travis build: #{travis_build_number}"

      sh "git commit -m \"#{message}\""
      sh "git remote add origin #{origin_url}"
      sh 'git push -f origin master:master'
    end
  end
end
