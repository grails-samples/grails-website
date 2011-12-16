databaseChangeLog = {

	changeSet(author: "pledbrook (generated)", id: "1322675860789-1") {
		createTable(tableName: "tutorial") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tutorialPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "date_created", type: "datetime") {
				constraints(nullable: "false")
			}

			column(name: "description", type: "longtext") {
				constraints(nullable: "false")
			}

			column(name: "featured", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "popularity_disliked", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "popularity_liked", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "popularity_net_liked", type: "integer") {
				constraints(nullable: "false")
			}

			column(name: "title", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "url", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1322675860789-2") {
		addColumn(tableName: "web_site") {
			column(name: "featured", type: "bit") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1323100866836-1") {
		createTable(tableName: "plugin_maven_repositories") {
			column(name: "plugin_id", type: "bigint")

			column(name: "maven_repositories_string", type: "varchar(255)")

			column(name: "maven_repositories_idx", type: "integer")
		}
	}
        
}
