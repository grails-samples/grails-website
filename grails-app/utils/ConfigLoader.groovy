class ConfigLoader {

    static void addEntries(Map data, obj = null) {
        data?.each { key, value ->
            if (value instanceof Map) {
                addEntries(value, obj.getProperty(key))
            }
            else obj.setProperty(key, value)
        }
    }
}
