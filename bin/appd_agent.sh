# Create AppDynamics folder
mkdir /opt/appdynamics/

# Change to /opt/appdynamics/
cd /opt/appdynamics

# Retrieve AppDynamics Java Agent and license file
wget https://s3.amazonaws.com/singlestone/binaries/AppServerAgent.zip
wget https://s3.amazonaws.com/singlestone/appdynamics/license.lic

# Unpack zip file
unzip AppServerAgent.zip

