require 'buildr/git_auto_version'
require 'buildr/gpg'
require 'buildr/single_intermediate_layout'
require 'buildr/gwt'
require 'buildr/jacoco'
require 'buildr/top_level_generate_dir'

TEST_DEPS = [:guiceyloops]
GWT_DEPS =
  [
    :elemental2_core,
    :elemental2_dom,
    :elemental2_promise,
    :jsinterop_base
  ]
GIN_DEPS =
  [
    :javax_inject,
    :gin,
    :aopalliance,
    :guice,
    :guice_assistedinject
  ]
DAGGER_DEPS =
  [
    :javax_inject,
    :dagger_core,
    :dagger_producers,
    :dagger_spi,
    :dagger_compiler,
    :googlejavaformat,
    :errorprone
  ]

# JDK options passed to test environment. Essentially turns assertions on.
AREZ_TEST_OPTIONS =
  {
    'braincheck.environment' => 'development',
    'arez.environment' => 'development'
  }

# Disable testing against J2CL until the build infrastructure can be upgraded
ENV['J2CL'] = 'no'

desc 'Arez: Simple, Scalable State Management Library'
define 'arez' do
  project.group = 'org.realityforge.arez'
  compile.options.source = '1.8'
  compile.options.target = '1.8'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  pom.add_apache_v2_license
  pom.add_github_project('arez/arez')
  pom.add_developer('realityforge', 'Peter Donald')

  desc 'Arez Core'
  define 'core' do
    pom.include_transitive_dependencies << artifact(:javax_annotation)
    pom.include_transitive_dependencies << artifact(:jsinterop_annotations)
    pom.include_transitive_dependencies << artifact(:braincheck)
    pom.dependency_filter = Proc.new {|dep| dep[:scope].to_s != 'test' && dep[:group] != 'org.realityforge.org.jetbrains.annotations'}

    compile.with :javax_annotation,
                 :braincheck,
                 :jetbrains_annotations,
                 :jsinterop_annotations

    test.options[:properties] =
      AREZ_TEST_OPTIONS.merge('arez.core.compile_target' => compile.target.to_s,
                              'arez.diagnostic_messages_file' => _('src/test/java/arez/diagnostic_messages.json'))
    test.options[:java_args] = ['-ea']

    gwt_enhance(project)

    package(:jar)
    package(:sources)
    package(:javadoc)

    test.using :testng
    test.compile.with TEST_DEPS, :jdepend, :javax_json
  end

  desc 'Test Arez API'
  define 'api-test' do
    test.compile.with :javax_annotation,
                      :javax_json,
                      :gir

    test.options[:properties] =
      AREZ_TEST_OPTIONS.merge(
        'arez.api_test.store_api_diff' => ENV['STORE_API_DIFF'] == 'true',
        'arez.prev.version' => ENV['PREVIOUS_PRODUCT_VERSION'],
        'arez.prev.jar' => artifact("org.realityforge.arez:arez-core:jar:#{ENV['PREVIOUS_PRODUCT_VERSION'] || project.version}").to_s,
        'arez.next.version' => ENV['PRODUCT_VERSION'],
        'arez.next.jar' => project('core').package(:jar).to_s,
        'arez.api_test.fixture_dir' => _('src/test/resources/fixtures').to_s,
        'arez.revapi.jar' => artifact(:revapi_diff).to_s
      )
    test.options[:java_args] = ['-ea']
    test.using :testng

    test.compile.enhance do
      mkdir_p _('src/test/resources/fixtures')
      artifact("org.realityforge.arez:arez-core:jar:#{ENV['PREVIOUS_PRODUCT_VERSION']}").invoke
      project('core').package(:jar).invoke
      artifact(:revapi_diff).invoke
    end unless ENV['TEST'] == 'no' || ENV['PRODUCT_VERSION'].nil? || ENV['PREVIOUS_PRODUCT_VERSION'].nil?

    test.exclude '*ApiDiffTest' if ENV['PRODUCT_VERSION'].nil? || ENV['PREVIOUS_PRODUCT_VERSION'].nil?

    project.jacoco.enabled = false
  end

  desc 'Arez Annotation processor'
  define 'processor' do
    pom.dependency_filter = Proc.new {|_| false}

    compile.with :javax_annotation,
                 :autoservice,
                 :autocommon,
                 :javapoet,
                 :guava

    test.with :compile_testing,
              Java.tools_jar,
              :truth,
              :junit,
              :hamcrest_core,
              DAGGER_DEPS,
              project('core').package(:jar),
              project('core').compile.dependencies

    package(:jar)
    package(:sources)
    package(:javadoc)

    package(:jar).enhance do |jar|
      jar.merge(artifact(:javapoet))
      jar.merge(artifact(:guava))
      jar.merge(artifact(:autocommon))
      jar.enhance do |f|
        shaded_jar = (f.to_s + '-shaded')
        Buildr.ant 'shade_jar' do |ant|
          artifact = Buildr.artifact(:shade_task)
          artifact.invoke
          ant.taskdef :name => 'shade', :classname => 'org.realityforge.ant.shade.Shade', :classpath => artifact.to_s
          ant.shade :jar => f.to_s, :uberJar => shaded_jar do
            ant.relocation :pattern => 'com.squareup.javapoet', :shadedPattern => 'arez.processor.vendor.javapoet'
            ant.relocation :pattern => 'com.google', :shadedPattern => 'arez.processor.vendor.google'
          end
        end
        FileUtils.mv shaded_jar, f.to_s
      end
    end

    test.using :testng
    test.options[:properties] = { 'arez.fixture_dir' => _('src/test/resources') }
    test.compile.with TEST_DEPS

    iml.test_source_directories << _('src/test/resources/input')
    iml.test_source_directories << _('src/test/resources/expected')
    iml.test_source_directories << _('src/test/resources/bad_input')
  end

  desc 'Arez Integration Tests'
  define 'integration-tests' do
    test.options[:properties] = AREZ_TEST_OPTIONS.merge('arez.integration_fixture_dir' => _('src/test/resources'))
    test.options[:java_args] = ['-ea']

    test.using :testng
    test.compile.with TEST_DEPS,
                      DAGGER_DEPS,
                      GWT_DEPS,
                      :javax_json,
                      :jsonassert,
                      :android_json,
                      project('core').package(:jar),
                      project('core').compile.dependencies,
                      project('processor').package(:jar),
                      project('processor').compile.dependencies

    # The generators are configured to generate to here.
    iml.test_source_directories << _('generated/processors/test/java')
  end

  desc 'Utilities to output of GWT when compiling Arez applications'
  define 'gwt-output-qa' do
    pom.include_transitive_dependencies << artifact(:gwt_symbolmap)
    pom.dependency_filter = Proc.new {|dep| %w(org.realityforge.gwt.symbolmap).include?(dep[:group].to_s)}

    compile.with :javax_annotation,
                 :javacsv,
                 :jetbrains_annotations,
                 :gwt_symbolmap,
                 :testng

    package(:jar)
    package(:sources)
    package(:javadoc)

    project.jacoco.enabled = false
  end

  desc 'Test Arez in downstream projects'
  define 'downstream-test' do
    compile.with :gir,
                 :javax_annotation

    test.options[:properties] =
      AREZ_TEST_OPTIONS.merge(
        'arez.prev.version' => ENV['PREVIOUS_PRODUCT_VERSION'] || project.version,
        'arez.next.version' => ENV['PRODUCT_VERSION'] || project.version,
        'arez.build_j2cl_variants' => (ENV['J2CL'] != 'no'),
        'arez.deploy_test.fixture_dir' => _('src/test/resources/fixtures').to_s,
        'arez.deploy_test.work_dir' => _(:target, 'deploy_test/workdir').to_s
      )
    test.options[:java_args] = ['-ea']

    local_test_repository_url = URI.join('file:///', project._(:target, :local_test_repository)).to_s
    compile.enhance do
      projects_to_upload = projects(%w(core processor))
      old_release_to = repositories.release_to
      begin
        # First we install them in a local repository so we don't have to access the network during local builds
        repositories.release_to = local_test_repository_url
        projects_to_upload.each do |prj|
          prj.packages.each do |pkg|
            # Uninstall version already present in local maven cache
            pkg.uninstall
            # Install version into local repository
            pkg.upload
          end
        end
        if ENV['STAGE_RELEASE'] == 'true'
          # Then we install it to a remote repository so that TravisCI can access the builds when it attempts
          # to perform a release
          repositories.release_to =
            { :url => 'https://stocksoftware.jfrog.io/stocksoftware/staging', :username => ENV['STAGING_USERNAME'], :password => ENV['STAGING_PASSWORD'] }
          projects_to_upload.each do |prj|
            prj.packages.each(&:upload)
          end
        end
      ensure
        repositories.release_to = old_release_to
      end
    end unless ENV['TEST'] == 'no' # These artifacts only required when running tests.

    test.compile.enhance do
      cp = project.compile.dependencies.map(&:to_s) + [project.compile.target.to_s]

      properties = {}
      # Take the version that we are releasing else fallback to project version
      properties['arez.prev.version'] = ENV['PREVIOUS_PRODUCT_VERSION'] || project.version
      properties['arez.next.version'] = ENV['PRODUCT_VERSION'] || project.version
      properties['arez.build_j2cl_variants'] = ENV['J2CL'] != 'no'
      properties['arez.deploy_test.work_dir'] = _(:target, 'deploy_test/workdir').to_s
      properties['arez.deploy_test.fixture_dir'] = _('src/test/resources/fixtures').to_s
      properties['arez.deploy_test.local_repository_url'] = local_test_repository_url
      properties['arez.deploy_test.store_statistics'] = ENV['STORE_BUILD_STATISTICS'] == 'true'
      properties['arez.deploy_test.build_before'] = (ENV['STORE_BUILD_STATISTICS'] != 'true' && ENV['BUILD_BEFORE'] != 'no')

      Java::Commands.java 'arez.downstream.CollectDrumLoopBuildStats', { :classpath => cp, :properties => properties } unless ENV['BUILD_STATS'] == 'no'
      Java::Commands.java 'arez.downstream.CollectFluxChallengeBuildStats', { :classpath => cp, :properties => properties } unless ENV['BUILD_STATS'] == 'no'
      Java::Commands.java 'arez.downstream.BuildDownstream', { :classpath => cp, :properties => properties } unless ENV['DOWNSTREAM'] == 'no'
      Java::Commands.java 'arez.downstream.CollectBuildStats', { :classpath => cp, :properties => properties } unless ENV['BUILD_STATS'] == 'no'
    end

    # Only run this test when preparing for release, never on TravisCI (as produces different byte sizes)
    test.exclude '*BuildStatsTest' if ENV['PRODUCT_VERSION'].nil? || ENV['BUILD_STATS'] == 'no' || !ENV['TRAVIS_BUILD_NUMBER'].nil?
    test.exclude '*BuildOutputTest' if ENV['BUILD_STATS'] == 'no'

    test.using :testng
    test.compile.with project('gwt-output-qa').package(:jar),
                      project('gwt-output-qa').compile.dependencies

    project.jacoco.enabled = false
  end

  desc 'Arez Examples used in documentation'
  define 'doc-examples' do
    project.enable_annotation_processor = true

    compile.with project('processor').package(:jar),
                 project('processor').compile.dependencies,
                 project('core').package(:jar),
                 project('core').compile.dependencies,
                 :gwt_user,
                 DAGGER_DEPS,
                 GWT_DEPS,
                 GIN_DEPS

    test.options[:properties] = AREZ_TEST_OPTIONS
    test.options[:java_args] = ['-ea']

    test.using :testng
    test.compile.with TEST_DEPS

    project.jacoco.enabled = false
  end

  doc.from(projects(%w(core processor))).
    using(:javadoc,
          :windowtitle => 'Arez API Documentation',
          :linksource => true,
          :timestamp => false,
          :link => %w(https://docs.oracle.com/javase/8/docs/api http://www.gwtproject.org/javadoc/latest/),
          :group => {
            'Core Packages' => 'arez:arez.spy*',
            'Component Packages' => 'arez.annotations*:arez.component*:arez.processor*'
          }
    )

  cleanup_javadocs(project, 'arez')

  iml.excluded_directories << project._('node_modules')
  iml.excluded_directories << project._('tmp')

  ipr.add_default_testng_configuration(:jvm_args => "-ea -Dbraincheck.environment=development -Darez.environment=development -Darez.output_fixture_data=false -Darez.deploy_test.build_before=true -Darez.fixture_dir=processor/src/test/resources -Darez.integration_fixture_dir=integration-tests/src/test/resources -Darez.api_test.fixture_dir=api-test/src/test/resources/fixtures -Darez.deploy_test.fixture_dir=downstream-test/src/test/resources/fixtures -Darez.deploy_test.work_dir=target/arez_downstream-test/deploy_test/workdir -Darez.prev.version=X -Darez.prev.jar=#{artifact("org.realityforge.arez:arez-core:jar:#{ENV['PREVIOUS_PRODUCT_VERSION'] || project.version}")} -Darez.next.version=X -Darez.next.jar=#{project('core').package(:jar)} -Darez.core.compile_target=target/arez_core/idea/classes -Darez.revapi.jar=#{artifact(:revapi_diff)} -Darez.diagnostic_messages_file=core/src/test/java/arez/diagnostic_messages.json")

  ipr.add_component_from_artifact(:idea_codestyle)

  ipr.nonnull_assertions = false

  ipr.add_component('NullableNotNullManager') do |component|
    component.option :name => 'myDefaultNullable', :value => 'javax.annotation.Nullable'
    component.option :name => 'myDefaultNotNull', :value => 'javax.annotation.Nonnull'
    component.option :name => 'myNullables' do |option|
      option.value do |value|
        value.list :size => '6' do |list|
          list.item :index => '0', :class => 'java.lang.String', :itemvalue => 'org.jetbrains.annotations.Nullable'
          list.item :index => '1', :class => 'java.lang.String', :itemvalue => 'javax.annotation.Nullable'
          list.item :index => '2', :class => 'java.lang.String', :itemvalue => 'javax.annotation.CheckForNull'
          list.item :index => '3', :class => 'java.lang.String', :itemvalue => 'org.springframework.lang.Nullable'
          list.item :index => '4', :class => 'java.lang.String', :itemvalue => 'edu.umd.cs.findbugs.annotations.Nullable'
          list.item :index => '5', :class => 'java.lang.String', :itemvalue => 'android.support.annotation.Nullable'
        end
      end
    end
    component.option :name => 'myNotNulls' do |option|
      option.value do |value|
        value.list :size => '4' do |list|
          list.item :index => '0', :class => 'java.lang.String', :itemvalue => 'org.jetbrains.annotations.NotNull'
          list.item :index => '1', :class => 'java.lang.String', :itemvalue => 'javax.annotation.Nonnull'
          list.item :index => '2', :class => 'java.lang.String', :itemvalue => 'edu.umd.cs.findbugs.annotations.NonNull'
          list.item :index => '3', :class => 'java.lang.String', :itemvalue => 'android.support.annotation.NonNull'
        end
      end
    end
  end
end

Buildr.projects.each do |project|
  unless project.name == 'arez'
    project.doc.options.merge!('Xdoclint:all' => true)
  end
end
