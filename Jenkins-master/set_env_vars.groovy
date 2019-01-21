import jenkins.model.*

def creds = com.cloudbees.plugins.credentials.CredentialsProvider.lookupCredentials(
  com.cloudbees.plugins.credentials.Credentials.class,
  Jenkins.instance,
  null,
  null
);
  def credential = creds.find {it.username == 'jenkins'}
                             
if (!credential) {
  return "Unable to pickup credential from Jenkins"
}

System.setProperty("jenkinsuserid", credential.id)

build.addAction(pa)
return credential.id;