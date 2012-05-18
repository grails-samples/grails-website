package org.grails.util

class ClusterService {
    private final nodeId

    ClusterService() {
        if (System.getenv("VCAP_APP_HOST")) {
            nodeId = System.getenv("VCAP_APP_HOST") + "##" + System.getenv("VCAP_APP_PORT")
        }
        else {
            nodeId = "12343456"
        }
    }

    String getNodeId() { return nodeId }
}
