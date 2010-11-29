changeSet(id:'WikiPageDeprecation', author:'pledbrook') {
    addColumn(tableName: "content") {
        column name: "deprecated", type: "boolean", {
            constraints nullable: true
        }

        column name: "deprecated_uri", type: "varchar(255)", {
            constraints nullable: true
        }
    }

    // All existing Wiki pages should have a default 'deprecated'
    // value of false, not null.
    update(tableName: "content") {
        column name: "deprecated", valueBoolean: false
        where "number is null"
    }
}
