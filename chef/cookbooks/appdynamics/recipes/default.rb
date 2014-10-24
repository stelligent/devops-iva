# Install AppDynamics

directory "/opt/appdynamics" do
	action :create
	owner 'tomcat'
	group 'tomcat'
	mode '777'
end

remote_file "/opt/appdynamics/Agent.zip" do
	source "https://s3.amazonaws.com/singlestone/appdynamics/AppServerAgent-3.9.4.0.zip"
	owner 'root'
	group 'root'
	mode "644"
end

execute "unzip JavaAgent" do
	cwd '/opt/appdynamics/'
	command "unzip Agent.zip"
end

directory "/opt/appdynamics/logs/tomcatinstance" do
	owner 'tomcat'
	group 'tomcat'
	mode '777'
	action :create
end

cookbook_file "/opt/appdynamics/conf/controller-info.xml" do
	source "controller-info.xml"
	owner 'root'
	group 'root'
	mode "0644"
	action :create
end