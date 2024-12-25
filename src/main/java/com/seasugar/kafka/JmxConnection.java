package com.seasugar.kafka;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

public class JmxConnection {

    private String ipPort;
    private String jmxUrl;
    private MBeanServerConnection conn;

//    public JmxConnection(String ipPort) {
//        this.ipPort = ipPort;
//    }

    public boolean init() {
        jmxUrl = "service:jmx:rmi:///jndi/rmi://10.86.40.215:9999/jmxrmi";

        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL(jmxUrl);
            JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL, null);
            conn = connector.getMBeanServerConnection();
            if (conn == null) {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public double getMsgInPerSec() {
        String objectName = "kafka.server:type=BrokerTopicMetrics.name=MssagesInPerSec";
        try {
            ObjectName mbeanName = new ObjectName(objectName);
            Object val = conn.getAttribute(mbeanName, "OneMinuteRate");
            if (val != null) {
                return (double) val;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0.0;
    }

    public static void main(String[] args) {
        JmxConnection jmxConnection = new JmxConnection();
        jmxConnection.init();
        System.out.println(jmxConnection.getMsgInPerSec());
    }
}
