databaseChangeLog = {
    delegate.databaseChangeLog logicalFilePath: "Initial changelog"

    changeSet(author: "pledbrook (generated)", id: "1333643132451-2") {
        createTable(tableName: "blog_entry") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "blog_entryPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "author", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "body", type: "longtext") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "locked", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "published", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-3") {
        createTable(tableName: "comment") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "commentPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "body", type: "longtext") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "poster_class", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "poster_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-4") {
        createTable(tableName: "comment_link") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "comment_linkPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "comment_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "comment_ref", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-5") {
        createTable(tableName: "content") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "contentPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "body", type: "longtext") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "locked", type: "bit")

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "class", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "author_id", type: "bigint")

            column(name: "current_id", type: "bigint")

            column(name: "number", type: "integer")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-6") {
        createTable(tableName: "download") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "downloadPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "beta_release", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "count", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "release_date", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "software_name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "software_version", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-7") {
        createTable(tableName: "download_file") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "download_filePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "download_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "files_idx", type: "integer")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-10") {
        createTable(tableName: "mirror") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "mirrorPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "file_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "url", type: "mediumblob") {
                constraints(nullable: "false")
            }

            column(name: "mirrors_idx", type: "integer")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-12") {
        createTable(tableName: "plugin") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pluginPK")
            }
            column(name: "version", type: "bigint") { constraints(nullable: "false") }
            column(name: "name", type: "varchar(255)") { constraints(nullable: "false", unique: "true") }
            column(name: "title", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "description_id", type: "bigint")
            column(name: "installation_id", type: "bigint")
            column(name: "faq_id", type: "bigint")
            column(name: "screenshots_id", type: "bigint")
            column(name: "author", type: "varchar(255)")
            column(name: "author_email", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "current_release", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "documentation_url", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "download_url", type: "varchar(255)") { constraints(nullable: "false") }
            column(name: "grails_version", type: "varchar(255)")
            column(name: "official", type: "bit") { constraints(nullable: "false") }
            column(name: "featured", type: "bit")
            column(name: "date_created", type: "datetime") { constraints(nullable: "false") }
            column(name: "last_updated", type: "datetime") { constraints(nullable: "false") }
            column(name: "last_released", type: "datetime")
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-16") {
        createTable(tableName: "rating") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "ratingPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-17") {
        createTable(tableName: "rating_link") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rating_linkPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "rating_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "rating_ref", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-18") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-20") {
        createTable(tableName: "screencast") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "screencastPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "longtext")

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "thumbnail_location", type: "varchar(255)")

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-21") {
        createTable(tableName: "screencast_mirror") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "screencast_miPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "longtext") {
                constraints(nullable: "false")
            }

            column(name: "location", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "screencast_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-22") {
        createTable(tableName: "tag_links") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tag_linksPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "tag_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "tag_ref", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "type", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-23") {
        createTable(tableName: "tags") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "tagsPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-27") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }

            column(name: "login", type: "varchar(15)") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-28") {
        createTable(tableName: "user_info") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "user_infoPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "email_subscribed", type: "bit") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "pledbrook (generated)", id: "1333643132451-30") {
        createTable(tableName: "user_role") {
            column(name: "user_roles_id", type: "bigint")

            column(name: "role_id", type: "bigint")
        }
    }
}
