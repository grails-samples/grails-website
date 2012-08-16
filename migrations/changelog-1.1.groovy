databaseChangeLog = {

    changeSet(author: "pledbrook (generated)", id: "1310034469414-3") {
        createTable(tableName: "role_permissions") {
            column(name: "role_id", type: "bigint")

            column(name: "permissions_string", type: "varchar(255)")
        }
    }

    // This changeset is required if the changelog is executed against an InnoDB database.
    // Without it, later changesets fail due to problems creating associated foreign keys.
    changeSet(author: "pledbrook", id: "FixRolePermissionsTableType") {
        sql('ALTER TABLE role_permissions ENGINE=MyISAM')
    }

    changeSet(author: "pledbrook (generated)", id: "1320427106545-1") {
        createTable(tableName: "bi_images") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "bi_imagesPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "data", type: "longblob") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320427106545-2") {
        createTable(tableName: "like_dislike") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "like_dislikePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "vote", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "what_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "what_type", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "who_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320427106545-5") {
        createTable(tableName: "web_site") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "web_sitePK")
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

    changeSet(author: "pledbrook (generated)", id: "1320427106545-6") {
        createTable(tableName: "web_site_bi_image") {
            column(name: "web_site_bi_image_id", type: "bigint")

            column(name: "image_id", type: "bigint")

            column(name: "bi_image_idx", type: "varchar(255)")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320427106545-18") {
        createIndex(indexName: "name_unique_1320427106197", tableName: "role", unique: "true") {
            column(name: "name")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1320427106545-19") {
        createIndex(indexName: "FKEAD9D23B75B39296", tableName: "role_permissions") {
            column(name: "role_id")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-28") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "role_permissions", constraintName: "FKEAD9D23B75B39296", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-32") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_info", constraintName: "FK1437D8A21ADE5676", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-33") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46A75B39296", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "pledbrook (generated)", id: "1310034469414-34") {
        addForeignKeyConstraint(baseColumnNames: "user_roles_id", baseTableName: "user_role", constraintName: "FK143BF46A16D0D038", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }
}
