# See http://docs.getchef.com/config_rb_knife.html for more information on knife configuration options

log_level                :info
log_location             "/opt/chef/knife.log"
node_name                "jenkins"
client_key               "/etc/.chef/client.pem"
validation_client_name   "ssc-validator"
validation_key           "/opt/chef/validator.pem"
chef_server_url          "https://chefserver.singlestonedemo.com/organizations/ssc"
cache_type               'BasicFile'
cache_options( :path => "#{ENV['HOME']}/.chef/checksums" )
cookbook_path            [ '/opt/chef/cookbooks' ]
