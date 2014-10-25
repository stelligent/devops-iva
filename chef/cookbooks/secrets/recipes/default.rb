app_secret = Chef::EncryptedDataBagItem.load_secret("#{node['app']['secretpath']}")
aws_id = Chef::EncryptedDataBagItem.load("app", "aws_id", app_secret)
aws_key = Chef::EncryptedDataBagItem.load("app", "aws_key", app_secret)
twilio_id = Chef::EncryptedDataBagItem.load("app", "twilio_id", app_secret)
twilio_token = Chef::EncryptedDataBagItem.load("app", "twilio_token", app_secret)

#aws_id["aws_access_key_id"]
#aws_key["aws_secret_key"]
#twilio_id["twilio_sid"]
#twilio_token["twilio_auth_token"]

# Set environment variables with decrypted data bag hashes
bash "set environment variables" do
	code <<-EOF
		export AWS_ACCESS_KEY_ID="#{aws_id}"
		export AWS_SECRET_KEY="#{aws_key}"
		export TWILIO_SID="#{twilio_id}"
		export TWILIO_AUTH_TOKEN="#{twilio_token}"
	EOF
end

template "/tmp/set_vars.sh" do
	source "set_vars.sh.erb"
	action :create
end

bash "execute environment variables" do
	user "root"
	cwd	"/tmp"
	code <<-EOH
	bash set_vars.sh
	EOH
end
