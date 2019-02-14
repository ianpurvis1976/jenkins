    import jenkins.model.*
    import hudson.model.*
    import hudson.security.*
    import hudson.plugins.*
    import hudson.plugins.active_directory.*
    import hudson.*
    import jenkins.*

    def instance = Jenkins.getInstance()

    String domain = 'iansdomain.com'
    String site = ''
    String server = 'win2016dc-2.iansdomain.com:3269'
    String bindName = ''
    String bindPassword = ''

    adrealm = new ActiveDirectorySecurityRealm(domain, 
        site, 
        bindName, 
        bindPassword, 
        server, 
        GroupLookupStrategy.RECURSIVE,
        false,	// Boolean removeIrrelevantGroups
        domain != null, // Boolean customDomain
        null, // CacheConfiguration cache,
        true, // Boolean startTls
        TlsConfiguration.JDK_TRUSTSTORE)
    instance.setSecurityRealm(ad_realm)
    instance.save())

    println "--> configure LDAP... done"