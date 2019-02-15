import java.lang.System
import jenkins.model.*
import hudson.plugins.active_directory.*
import com.google.common.collect.Lists

def domain = "iansdomain.com"
def dc = "win2016dc-2.iansdomain.com"
def site = ""
def bindName = ""
def bindPassword = ""
def server = "win2016dc-2.iansdomain.com:389"
def groupLookupStrategy = "AUTO"
def tlsConfiguration = "JDK_TRUSTSTORE"


def instance = Jenkins.getInstance()
def ActiveDirectoryDomain adDomain = new ActiveDirectoryDomain(domain, server);
def domains = new ArrayList<ActiveDirectoryDomain>();
domains.add(adDomain);

println "--> Configure AD"
ActiveDirectorySecurityRealm realm = new ActiveDirectorySecurityRealm(domain,
                                                                      domains,
                                                                      site,
                                                                      bindName,
                                                                      bindPassword,
                                                                      'win2016dc-2.iansdomain.com:389',
                                                                      GroupLookupStrategy.valueOf(groupLookupStrategy.toString().toUpperCase()),
							                                                        false,
                                                                      domain!=null,
									                                                    null,
									                                                    false
                                                                      //TlsConfiguration.valueOf(tlsConfiguration.toString().toUpperCase())
                                                                      )
                    
//realm.getDomains().each({
//  it.bindName = realm.bindName
//  it.bindPassword = realm.bindPassword
//})
  
Jenkins.instance.setSecurityRealm(realm)
Jenkins.instance.save()