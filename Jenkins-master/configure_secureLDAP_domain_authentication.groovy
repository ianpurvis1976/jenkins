import java.lang.System
import jenkins.model.*
import hudson.plugins.active_directory.*
import com.google.common.collect.Lists



def enabled = false
def domain = "iansdomain.com"
def site = null
def bindName = null
def bindPassword = null
def server = "win2016dc-2.iansdomain.com:3269"
def groupLookupStrategy = "RECURSIVE"
def tlsConfiguration = "JDK_TRUSTSTORE"

def instance = Jenkins.getInstance()


println "--> Configure AD"
ActiveDirectorySecurityRealm realm = new ActiveDirectorySecurityRealm(domain,
                                                                      Lists.newArrayList(new ActiveDirectoryDomain(domain, server)),
                                                                      site,
                                                                      bindName,
                                                                      bindPassword,
                                                                      server,
                                                                      GroupLookupStrategy.valueOf(groupLookupStrategy.toString().toUpperCase()),
							                                          false,									                                    false,
									                                  null,
									                                  true,
                                                                      TlsConfiguration.valueOf(tlsConfiguration.toString().toUpperCase())
                                                                      )
                    
realm.getDomains().each({
  it.bindName = realm.bindName
  it.bindPassword = realm.bindPassword
})
  
Jenkins.instance.setSecurityRealm(realm)
Jenkins.instance.save()