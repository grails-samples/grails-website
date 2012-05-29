package org.grails.util

class ClusterService {
    private final String nodeId

    ClusterService() {
        if (System.getenv("VCAP_APP_HOST")) {
            nodeId = System.getenv("VCAP_APP_HOST") + "##" + System.getenv("VCAP_APP_PORT")
        }
        else {
            nodeId = "123434##6"
        }
    }

    String getNodeId() { return nodeId }
}
