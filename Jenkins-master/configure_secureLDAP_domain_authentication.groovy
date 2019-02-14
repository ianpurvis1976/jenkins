import java.lang.System
import jenkins.model.*
import hudson.plugins.active_directory.*
import com.google.common.collect.Lists



string domain = 'iansdomain.com'
string site = null
string bindName = null
string bindPassword = null
string server = 'win2016dc-2.iansdomain.com:389'
string groupLookupStrategy = "AUTOMATIC"
string tlsConfiguration = "JDK_TRUSTSTORE"

def instance = Jenkins.getInstance()


println "--> Configure AD"
ActiveDirectorySecurityRealm realm = new ActiveDirectorySecurityRealm(domain,
                                                                      site,
                                                                      bindName,
                                                                      bindPassword,
                                                                      server,
                                                                      GroupLookupStrategy.valueOf(groupLookupStrategy.toString().toUpperCase()),
							                                                        false,
                                                                      false,
									                                                    null,
									                                                    false
                                                                      )
                    
realm.getDomains().each({
  it.bindName = realm.bindName
  it.bindPassword = realm.bindPassword
})
  
Jenkins.instance.setSecurityRealm(realm)
Jenkins.instance.save()