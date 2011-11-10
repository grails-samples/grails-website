databaseChangeLog = {

	changeSet(author: "pledbrook (generated)", id: "1320944813799-1") {
		createTable(tableName: "license") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "licensePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "url", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-2") {
		createTable(tableName: "plugin_license") {
			column(name: "plugin_licenses_id", type: "bigint")

			column(name: "license_id", type: "bigint")
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-3") {
		addColumn(tableName: "plugin") {
			column(name: "organization", type: "varchar(255)")
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-4") {
		addColumn(tableName: "plugin") {
			column(name: "organization_url", type: "varchar(255)")
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-15") {
		createIndex(indexName: "FK5A8AB7355E175809", tableName: "plugin_license") {
			column(name: "license_id")
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-16") {
		createIndex(indexName: "FK5A8AB735B49DE880", tableName: "plugin_license") {
			column(name: "plugin_licenses_id")
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-26") {
		addForeignKeyConstraint(baseColumnNames: "license_id", baseTableName: "plugin_license", constraintName: "FK5A8AB7355E175809", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "license", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-27") {
		addForeignKeyConstraint(baseColumnNames: "plugin_licenses_id", baseTableName: "plugin_license", constraintName: "FK5A8AB735B49DE880", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "plugin", referencesUniqueColumn: "false")
	}

        //--------------------------------------------
        // Random leftover stuff
        //
	changeSet(author: "pledbrook (generated)", id: "1320944813799-5") {
		addNotNullConstraint(columnDataType: "longtext", columnName: "body", tableName: "comment")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-6") {
		addNotNullConstraint(columnDataType: "datetime", columnName: "date_created", tableName: "comment")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-7") {
		addNotNullConstraint(columnDataType: "datetime", columnName: "last_updated", tableName: "comment")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-8") {
		addNotNullConstraint(columnDataType: "varchar(255)", columnName: "poster_class", tableName: "comment")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-9") {
		addNotNullConstraint(columnDataType: "bigint", columnName: "poster_id", tableName: "comment")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-10") {
		addNotNullConstraint(columnDataType: "bit", columnName: "beta_release", tableName: "download")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-11") {
		addNotNullConstraint(columnDataType: "bit", columnName: "featured", tableName: "plugin")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-12") {
		addNotNullConstraint(columnDataType: "bit", columnName: "official", tableName: "plugin")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-13") {
		addNotNullConstraint(columnDataType: "bit", columnName: "zombie", tableName: "plugin")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-14") {
		createIndex(indexName: "name_unique_1320944812248", tableName: "plugin", unique: "true") {
			column(name: "name")
		}
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-17") {
		addForeignKeyConstraint(baseColumnNames: "comment_id", baseTableName: "comment_link", constraintName: "FK2F689DDAF37C8E8A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "comment", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-18") {
		addForeignKeyConstraint(baseColumnNames: "author_id", baseTableName: "content", constraintName: "FK38B734797B9748B6", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-19") {
		addForeignKeyConstraint(baseColumnNames: "current_id", baseTableName: "content", constraintName: "FK38B73479839030FB", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "content", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-20") {
		addForeignKeyConstraint(baseColumnNames: "download_id", baseTableName: "download_file", constraintName: "FK1DFFAD33CFDB47", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "download", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-21") {
		addForeignKeyConstraint(baseColumnNames: "file_id", baseTableName: "mirror", constraintName: "FKBFFD6BBF550BF0EF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "download_file", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-22") {
		addForeignKeyConstraint(baseColumnNames: "description_id", baseTableName: "plugin", constraintName: "FKC5476F33343A5DCF", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "content", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-23") {
		addForeignKeyConstraint(baseColumnNames: "faq_id", baseTableName: "plugin", constraintName: "FKC5476F33CD28C875", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "content", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-24") {
		addForeignKeyConstraint(baseColumnNames: "installation_id", baseTableName: "plugin", constraintName: "FKC5476F33B7D872D1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "content", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-25") {
		addForeignKeyConstraint(baseColumnNames: "screenshots_id", baseTableName: "plugin", constraintName: "FKC5476F33FA44DC5E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "content", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-28") {
		addForeignKeyConstraint(baseColumnNames: "rating_id", baseTableName: "rating_link", constraintName: "FK1827315C45884E64", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "rating", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-29") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "role_permissions", constraintName: "FKEAD9D23B75B39296", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-30") {
		addForeignKeyConstraint(baseColumnNames: "video_host_id", baseTableName: "screencast", constraintName: "FKE72625AB937EA8A1", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "video_host", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-31") {
		addForeignKeyConstraint(baseColumnNames: "screencast_id", baseTableName: "screencast_mirror", constraintName: "FK326C9CD3A67C8B4A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "screencast", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-32") {
		addForeignKeyConstraint(baseColumnNames: "tag_id", baseTableName: "tag_links", constraintName: "FK7C35D6D45A3B441D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "tags", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-33") {
		addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_info", constraintName: "FK1437D8A21ADE5676", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-34") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46A75B39296", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-35") {
		addForeignKeyConstraint(baseColumnNames: "user_roles_id", baseTableName: "user_role", constraintName: "FK143BF46A16D0D038", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
	}

	changeSet(author: "pledbrook (generated)", id: "1320944813799-36") {
		dropColumn(columnName: "url", tableName: "mirror")
	}
}
