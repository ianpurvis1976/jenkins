    import jenkins.model.*
    import hudson.security.*
    import hudson.plugins.*
    import hudson.plugins.active_directory.*
    import hudson.*
    import jenkins.*

    String domain = 'iansdomain.com'
    String site = ''
    String server = 'win2016dc-2.iansdomain.com:3269'
    String bindName = ''
    String bindPassword = 'password'
    String tlsConfiguration = 'JDKTrustStore' 

    SecurityRealm ad_realm = new ActiveDirectorySecurityRealm(domain, site, bindName, bindPassword, server, tlsConfiguration)
    jenkins.instance.setSecurityRealm(ad_realm)