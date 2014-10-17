domain=`hostname --fqdn`
public_ip=$(ifconfig eth0 | grep inet | awk '{print $2}' | cut -d ':' -f2)

# change hostname
cat > /etc/sysconfig/network <<EOF
NETWORKING=yes
HOSTNAME=$domain
NETWORKING_IPV6=no
NOZEROCONF=yes
EOF
  
cat > /etc/hosts <<EOF
127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
::1         localhost localhost.localdomain localhost6 localhost6.localdomain6
$public_ip  $domain
EOF
  
echo $domain | tee /etc/hostname
hostname $domain
service network restart