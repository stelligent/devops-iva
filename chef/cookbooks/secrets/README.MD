Secrets Cookbook
================
This cookbook is designed to decrypt a data bag containing API credentials for Amazon DynamoDB and Twilio.


Requirements
------------
The secret key file must exist in /opt/chef prior to executing this recipe.


Attributes
----------
default['app']['secretpath'] is the location of the secret key on the node filesystem.


License and Authors
-------------------
Author: Dave Tashner 2014
License: MIT license