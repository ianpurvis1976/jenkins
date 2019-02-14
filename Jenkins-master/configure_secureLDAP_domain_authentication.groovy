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

    adrealm = new ActiveDirectorySecurityRealm(domain, site, bindName, bindPassword, server)
    instance.setSecurityRealm(adrealm)

    println "--> configure LDAP... done"