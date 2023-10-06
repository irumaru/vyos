interfaces {
    ethernet eth0 {
        address 192.168.150.42/24
        ipv6 {
            address {
                autoconf
            }
        }
    }
    ethernet eth1 {
        address 192.168.172.1/24
    }
    loopback lo {
    }
    wireguard wg0 {
        address 192.168.161.2/24
        description to-vyos-1
        peer to-gw1 {
            address 2001:f71:b200:a51:1c55:37ff:fe2e:238
            allowed-ips 192.168.0.0/16
            allowed-ips 224.0.0.5/32
            allowed-ips 224.0.0.6/32
            port 51820
            pubkey C6z6+fs6QMouZlZiI6+cKN9lVV2rxkJrFRENJMcTmjU=
        }
        port 51820
        private-key nclab
    }
}
nat {
    source {
        rule 100 {
            outbound-interface eth0
            source {
                address 192.168.172.0/24
            }
            translation {
                address masquerade
            }
        }
    }
}
protocols {
    ospf {
        area 0 {
            network 192.168.172.0/24
            network 192.168.161.0/24
        }
        parameters {
            abr-type cisco
            router-id 192.168.172.1
        }
    }
    static {
        interface-route 192.168.171.0/24 {
            next-hop-interface wg0 {
                disable
            }
        }
        route 0.0.0.0/0 {
            next-hop 192.168.150.1 {
            }
        }
    }
}
service {
    ssh {
        port 22
    }
}
system {
    config-management {
        commit-revisions 100
    }
    conntrack {
        modules {
            ftp
            h323
            nfs
            pptp
            sip
            sqlnet
            tftp
        }
    }
    console {
        device ttyS0 {
            speed 115200
        }
    }
    host-name vyos-2
    login {
        user vyos {
            authentication {
                encrypted-password $6$yxOe3hvj0gxJHTTV$FuA3iJh2RZUc/Lsd4G82h5g/nna5smjcMYlassSyV4Q3e9Q11GcUIhdK/wqAyKhLQkVb89tL2s6po5wGe8V121
            }
        }
    }
    ntp {
        server time1.vyos.net {
        }
        server time2.vyos.net {
        }
        server time3.vyos.net {
        }
    }
    syslog {
        global {
            facility all {
                level info
            }
            facility protocols {
                level debug
            }
        }
    }
}


// Warning: Do not remove the following line.
// vyos-config-version: "broadcast-relay@1:cluster@1:config-management@1:conntrack@3:conntrack-sync@2:container@1:dhcp-relay@2:dhcp-server@6:dhcpv6-server@1:dns-forwarding@3:firewall@5:https@2:interfaces@22:ipoe-server@1:ipsec@5:isis@1:l2tp@3:lldp@1:mdns@1:nat@5:ntp@1:pppoe-server@5:pptp@2:qos@1:quagga@8:rpki@1:salt@1:snmp@2:ssh@2:sstp@3:system@21:vrrp@2:vyos-accel-ppp@2:wanloadbalance@3:webproxy@2:zone-policy@1"
// Release version: 1.3.3