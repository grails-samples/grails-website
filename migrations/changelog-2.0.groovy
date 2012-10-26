    databaseChangeLog = {

        changeSet(author: "eberry (generated)", id: "1334328604860-1") {
            addColumn(tableName: "download") {
                column(name: "latest_release", type: "bit")
            }

            // Make 2.0.4 the latest download on startup.
            update(tableName: "download") {
                column name: "latest_release", valueBoolean: "true"
                where "software_name = 'Grails' and software_version = '2.0.4'"
            }
        }

        changeSet(author: "eberry (generated)", id: "1334328604860-2") {
            addColumn(tableName: "download_file") {
                column(name: "file_type", type: "integer") {
                    constraints(nullable: "false")
                }
            }

            // Move documentation downloads down a level, making them children
            // of a 'Grails' download. So now a download has binary, documentation
            // and optionally source files/packages.
            grailsChange {
                change {
                    def rows = sql.rows "SELECT * FROM download"
                    for (r in rows.findAll { it.software_name == "Grails Documentation" }) {
                        def distributionId = rows.find { it.software_name == "Grails" && it.software_version == r.software_version }.id
                        def maxFileIndex = sql.firstRow "SELECT max(files_idx) FROM download_file WHERE download_id = ${distributionId}"
                        maxFileIndex = maxFileIndex[0].toInteger()

                        sql.executeUpdate "UPDATE download_file SET download_id = ${distributionId}, " +
                                "title = 'Documentation', files_idx = ${maxFileIndex + 1} WHERE download_id = ${r.id}"
                    }

                    sql.executeUpdate "DELETE FROM download WHERE software_name = 'Grails Documentation'"
                }
                confirm "Migrated Grails Documentation download files"
            }

            // Set file type to SOURCE for those identifiable as such.
            update(tableName: "download_file") {
                column name: "file_type", value: "1"
                where "title like 'Source%'"
            }

            // Set file type to DOCUMENTATION for those identifiable as such.
            update(tableName: "download_file") {
                column name: "file_type", value: "2"
                where "title = 'Documentation'"
            }
        }

        changeSet(author: "eberry (generated)", id: "1334328604860-3") {
            dropNotNullConstraint(columnDataType: "datetime", columnName: "release_date", tableName: "download")
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-1") {
            createTable(tableName: "generic_approval_response") {
                column(autoIncrement: "true", name: "id", type: "bigint") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "generic_approPK")
                }

                column(name: "version", type: "bigint") { constraints(nullable: "false") }
                column(name: "date_created", type: "datetime") { constraints(nullable: "false") }
                column(name: "moderated_by_id", type: "bigint") { constraints(nullable: "false") }
                column(name: "response_text", type: "varchar(255)") { constraints(nullable: "false") }
                column(name: "status", type: "varchar(255)") { constraints(nullable: "false") }
                column(name: "submitted_by_id", type: "bigint") { constraints(nullable: "false") }
                column(name: "what_id", type: "bigint") { constraints(nullable: "false") }
                column(name: "what_type", type: "varchar(255)") { constraints(nullable: "false") }
            }

            sql("ALTER TABLE generic_approval_response ENGINE=InnoDB")
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-2") {
            createTable(tableName: "plugin_pending_approval") {
                column(autoIncrement: "true", name: "id", type: "bigint") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "plugin_pendinPK")
                }

                column(name: "version", type: "bigint") { constraints(nullable: "false") }
                column(name: "date_created", type: "datetime") { constraints(nullable: "false") }
                column(name: "name", type: "varchar(255)") { constraints(nullable: "false") }
                column(name: "notes", type: "longtext")
                column(name: "scm_url", type: "varchar(255)") { constraints(nullable: "false") }
                column(name: "status", type: "varchar(255)") { constraints(nullable: "false") }
                column(name: "submitted_by_id", type: "bigint") { constraints(nullable: "false") }
                column(name: "version_number", type: "varchar(255)") { constraints(nullable: "false") }
            }

            sql('ALTER TABLE plugin_pending_approval ENGINE=InnoDB')
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-8") {
            addColumn(tableName: "screencast") {
                column(name: "popularity_disliked", type: "integer") { constraints(nullable: "false") }
                column(name: "popularity_liked", type: "integer") { constraints(nullable: "false") }
                column(name: "popularity_net_liked", type: "integer") { constraints(nullable: "false") }
                column(name: "status", type: "varchar(255)", value: "APPROVED")
                column(name: "submitted_by_id", type: "bigint", valueNumeric: "1") { constraints(nullable: "false") }
            }

            dropColumn(columnName: "thumbnail_location", tableName: "screencast")
            createIndex(indexName: "FKE72625AB8FF3BE26", tableName: "screencast") {
                column(name: "submitted_by_id")
            }
            dropTable(tableName: "screencast_mirror")
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-11") {
            addColumn(tableName: "tutorial") {
                column(name: "last_updated", type: "datetime", valueDate: "2012-06-22") { constraints(nullable: "false") }
                column(name: "status", type: "varchar(255)", value: "APPROVED")
                column(name: "submitted_by_id", type: "bigint", valueNumeric: "1") { constraints(nullable: "false") }
            }
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-13") {
            addColumn(tableName: "web_site") {
                column(name: "short_description", type: "varchar(150)")
                column(name: "status", type: "varchar(255)", value: "APPROVED") { constraints(nullable: "false") }
                column(name: "submitted_by_id", type: "bigint", valueNumeric: "1") {
                    constraints(nullable: "false")
                }
            }
        }

        changeSet(author: "pledbrook", id: "ReleaseStatusForPendingPlugins") {
            addColumn(tableName: "pending_release") {
                column(name: "date_created", type: "datetime") { constraints(nullable: "false") }
                column(name: "status", type: "varchar(255)", value: "COMPLETED") { constraints(nullable: "false") }
            }

            dropNotNullConstraint tableName: "pending_release", columnName: "zip", columnDataType: "longblob"
            dropNotNullConstraint tableName: "pending_release", columnName: "pom", columnDataType: "mediumblob"
            dropNotNullConstraint tableName: "pending_release", columnName: "xml", columnDataType: "mediumblob"
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-16") {
            createIndex(indexName: "FK2F0606558FF3BE26", tableName: "generic_approval_response") {
                column(name: "submitted_by_id")
            }
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-17") {
            createIndex(indexName: "FK2F060655E3AC4C6C", tableName: "generic_approval_response") {
                column(name: "moderated_by_id")
            }
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-18") {
            createIndex(indexName: "FKFE53C7778FF3BE26", tableName: "plugin_pending_approval") {
                column(name: "submitted_by_id")
            }
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-19") {
            createIndex(indexName: "unique-name", tableName: "plugin_pending_approval") {
                column(name: "version_number")
                column(name: "name")
            }
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-21") {
            createIndex(indexName: "FKB852B5E8FF3BE26", tableName: "tutorial") {
                column(name: "submitted_by_id")
            }
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-22") {
            createIndex(indexName: "FKD52CC1528FF3BE26", tableName: "web_site") {
                column(name: "submitted_by_id")
            }
        }

        changeSet(author: "grocher", id: "AddStatusColumnToContentTable") {
            addColumn(tableName: "content") {
                column(name: "status", type: "varchar(255)", value: "APPROVED") { constraints(nullable: "true") }
            }
        }

        changeSet(author: "grocher", id: "AddEnabledColumnToUserTable") {
            addColumn(tableName: "user") {
                column(name: "enabled", type: "bit", valueBoolean: "true") { constraints(nullable: "false") }
            }
        }

        changeSet(author: "pledbrook (generated)", id: "1340116940515-51") {
            dropColumn(columnName: "featured", tableName: "tutorial")
        }

    changeSet(author: "pledbrook", id: "EnhancePluginReleaseTable") {
        addColumn(tableName: "plugin_release") {
            column(name: "is_snapshot", type: "bit", valueBoolean: "false") { constraints nullable: "false" }
        }

        // Fix values for new columns for snapshot releases, i.e. those whose
        // version ends with '-SNAPSHOT'.
        sql "UPDATE plugin_release SET is_snapshot = true WHERE release_version like '%-SNAPSHOT%'"
    }

        changeSet(author: "craig (generated)", id: "1345809360198-3") {
            createTable(tableName: "testimonial") {
                column(autoIncrement: "true", name: "id", type: "bigint") {
                    constraints(nullable: "false", primaryKey: "true", primaryKeyName: "testimonialPK")
                }

                column(name: "version", type: "bigint") {
                    constraints(nullable: "false")
                }

                column(name: "company_name", type: "varchar(255)") {
                    constraints(nullable: "false")
                }

                column(name: "body", type: "longtext") {
                    constraints(nullable: "false")
                }

                column(name: "date_created", type: "datetime") {
                    constraints(nullable: "false")
                }

                column(name: "featured", type: "bit") {
                    constraints(nullable: "false")
                }

                column(name: "last_updated", type: "datetime") {
                    constraints(nullable: "false")
                }

                column(name: "status", type: "varchar(255)")

                column(name: "submitted_by_id", type: "bigint") {
                    constraints(nullable: "false")
                }

                column(name: "title", type: "varchar(50)") {
                    constraints(nullable: "false")
                }
            }
        }


    }
