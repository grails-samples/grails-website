import org.springframework.cloud.CloudFactory

def cloud

try {
  cloud = new CloudFactory().cloud
} catch(e) {}

grails.serverURL = "http://plugins-grails-org-dev.cfapps.io/"

// configure sendgrid SMTP sender
grails {
   mail {
     def smtpServiceInfo = cloud.getServiceInfo('sendgrid')
     host = "smtp.sendgrid.net"
     port = 587
     username = smtpServiceInfo.userName
     password = smtpServiceInfo.password
     props = ["mail.smtp.starttls.enable":"true", 
              "mail.smtp.port":"587"]
   }
}