// Switches database tables over to InnoDB.
databaseChangeLog = {

    changeSet(author: 'burt', id: '2012-06-30-INNODB-1') {
        // shouldn't have been created - from test domain classes in the rateable plugin that should be excluded
        dropTable(tableName: 'test_domain')
        dropTable(tableName: 'test_rater')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-2') {
        sql('ALTER TABLE comment ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-3') {

        sql('ALTER TABLE comment_link ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FK2F689DDAF37C8E8A',
           baseTableName: 'comment_link', baseColumnNames: 'comment_id',
           referencedTableName: 'comment', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-4') {
        sql('ALTER TABLE user ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-5') {

        sql('ALTER TABLE content ENGINE=InnoDB')

        // duplicate, not used
        dropIndex(tableName: 'content', indexName: 'FK38B73479BC488994')

        // mapping 'body type:"text"'
        modifyDataType(tableName: 'content', columnName: 'body',
                       newDataType: 'longtext not null')

        addForeignKeyConstraint(constraintName: 'FK38B73479839030FB',
           baseTableName: 'content', baseColumnNames: 'current_id',
           referencedTableName: 'content', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK38B734797B9748B6',
           baseTableName: 'content', baseColumnNames: 'author_id',
           referencedTableName: 'user', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-6') {
        sql('ALTER TABLE download ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-7') {

        sql('ALTER TABLE download_file ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FK1DFFAD33CFDB47',
           baseTableName: 'download_file', baseColumnNames: 'download_id',
           referencedTableName: 'download', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-8') {
        sql('ALTER TABLE license ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-9') {

        sql('ALTER TABLE mirror ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FKBFFD6BBF550BF0EF',
           baseTableName: 'mirror', baseColumnNames: 'file_id',
           referencedTableName: 'download_file', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-10') {

        sql('ALTER TABLE pending_release ENGINE=InnoDB')

        // constraint 'size:0..50000000'
        modifyDataType(tableName: 'pending_release', columnName: 'zip',
                       newDataType: 'longblob not null')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-11') {

        sql('ALTER TABLE plugin ENGINE=InnoDB')

        // mapping "summary type: 'text'"
        modifyDataType(tableName: 'plugin', columnName: 'summary',
                       newDataType: 'longtext')

        // can't rename, so drop and re-create with new name
        dropIndex(tableName: 'plugin', indexName: 'FKC5476F33DDE70EC0')
        createIndex(tableName: 'plugin', indexName: 'FKC5476F33FA44DC5E') {
            column(name: 'screenshots_id')
        }

        addForeignKeyConstraint(constraintName: 'FKC5476F33FA44DC5E',
           baseTableName: 'plugin', baseColumnNames: 'screenshots_id',
           referencedTableName: 'content', referencedColumnNames: 'id')

        // can't rename, so drop and re-create with new name
        dropIndex(tableName: 'plugin', indexName: 'FKC5476F339B7AA533')
        createIndex(tableName: 'plugin', indexName: 'FKC5476F33B7D872D1') {
            column(name: 'installation_id')
        }

        addForeignKeyConstraint(constraintName: 'FKC5476F33B7D872D1',
           baseTableName: 'plugin', baseColumnNames: 'installation_id',
           referencedTableName: 'content', referencedColumnNames: 'id')

        // can't rename, so drop and re-create with new name
        dropIndex(tableName: 'plugin', indexName: 'FKC5476F3317DC9031')
        createIndex(tableName: 'plugin', indexName: 'FKC5476F33343A5DCF') {
            column(name: 'description_id')
        }

        addForeignKeyConstraint(constraintName: 'FKC5476F33343A5DCF',
           baseTableName: 'plugin', baseColumnNames: 'description_id',
           referencedTableName: 'content', referencedColumnNames: 'id')

        // can't rename, so drop and re-create with new name
        dropIndex(tableName: 'plugin', indexName: 'FKC5476F33B0CAFAD7')
        createIndex(tableName: 'plugin', indexName: 'FKC5476F33CD28C875') {
            column(name: 'faq_id')
        }

        addForeignKeyConstraint(constraintName: 'FKC5476F33CD28C875',
           baseTableName: 'plugin', baseColumnNames: 'faq_id',
           referencedTableName: 'content', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-12') {

        sql('ALTER TABLE plugin_license ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FK5A8AB7355E175809',
           baseTableName: 'plugin_license', baseColumnNames: 'license_id',
           referencedTableName: 'license', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK5A8AB735B49DE880',
           baseTableName: 'plugin_license', baseColumnNames: 'plugin_licenses_id',
           referencedTableName: 'plugin', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-13') {

        sql('ALTER TABLE plugin_maven_repositories ENGINE=InnoDB')

        // seems like a good idea:
        createIndex(tableName: 'plugin_maven_repositories', indexName: 'fk_plugin_maven_repositories') {
            column(name: 'plugin_id')
        }
        addForeignKeyConstraint(constraintName: 'fk_plugin_maven_repositories',
           baseTableName: 'plugin_maven_repositories', baseColumnNames: 'plugin_id',
           referencedTableName: 'plugin', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-14') {

        sql('ALTER TABLE plugin_release ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FK919B5AFB2D0CED0B',
           baseTableName: 'plugin_release', baseColumnNames: 'plugin_id',
           referencedTableName: 'plugin', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-15') {
        sql('ALTER TABLE role ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-16') {

        sql('ALTER TABLE role_permissions ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FKEAD9D23B75B39296',
           baseTableName: 'role_permissions', baseColumnNames: 'role_id',
           referencedTableName: 'role', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-17') {
        sql('ALTER TABLE tags ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-18') {

        sql('ALTER TABLE tag_links ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FK7C35D6D45A3B441D',
           baseTableName: 'tag_links', baseColumnNames: 'tag_id',
           referencedTableName: 'tags', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-19') {

        sql('ALTER TABLE user_info ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FK1437D8A21ADE5676',
           baseTableName: 'user_info', baseColumnNames: 'user_id',
           referencedTableName: 'user', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-20') {

        sql('ALTER TABLE user_permissions ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FKE693E6101ADE5676',
           baseTableName: 'user_permissions', baseColumnNames: 'user_id',
           referencedTableName: 'user', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-21') {

        sql('ALTER TABLE user_role ENGINE=InnoDB')

        addForeignKeyConstraint(constraintName: 'FK143BF46A16D0D038',
           baseTableName: 'user_role', baseColumnNames: 'user_roles_id',
           referencedTableName: 'user', referencedColumnNames: 'id')

        addForeignKeyConstraint(constraintName: 'FK143BF46A75B39296',
           baseTableName: 'user_role', baseColumnNames: 'role_id',
           referencedTableName: 'role', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-22') {

        sql('ALTER TABLE version_order ENGINE=InnoDB')

        // duplicate, not used
        dropIndex(tableName: 'version_order', indexName: 'order_index_unique_1311929797898')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-23') {
        sql('ALTER TABLE wiki_image ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-24') {

        sql('ALTER TABLE video_host ENGINE=InnoDB')

        // constraint 'maxSize: 5000'
        modifyDataType(tableName: 'video_host', columnName: 'embed_template',
                       newDataType: 'varchar(5000) not null')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-25') {

        sql('ALTER TABLE screencast ENGINE=InnoDB')

        // can't rename, so drop and re-create with new name
        dropIndex(tableName: 'screencast', indexName: 'FKE72625AB937EA8A1')
        createIndex(tableName: 'screencast', indexName: 'FKE72625AB8F5FAE40') {
            column(name: 'video_host_id')
        }

        addForeignKeyConstraint(constraintName: 'FKE72625AB8F5FAE40',
           baseTableName: 'screencast', baseColumnNames: 'video_host_id',
           referencedTableName: 'video_host', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-26') {

        sql('ALTER TABLE screencast_mirror ENGINE=InnoDB')

        // can't rename, so drop and re-create with new name
        dropIndex(tableName: 'screencast_mirror', indexName: 'FK326C9CD3A67C8B4A')
        createIndex(tableName: 'screencast_mirror', indexName: 'FK326C9CD326BC398B') {
            column(name: 'screencast_id')
        }

        addForeignKeyConstraint(constraintName: 'FK326C9CD326BC398B',
           baseTableName: 'screencast_mirror', baseColumnNames: 'screencast_id',
           referencedTableName: 'screencast', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-27') {

        sql('ALTER TABLE web_site ENGINE=InnoDB')

        // constraint 'maxSize: 50'
        modifyDataType(tableName: 'web_site', columnName: 'title',
                       newDataType: 'varchar(50) not null')

        // constraint 'maxSize: 5000'
        modifyDataType(tableName: 'web_site', columnName: 'description',
                       newDataType: 'varchar(5000) not null')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-28') {

        sql('ALTER TABLE tutorial ENGINE=InnoDB')

        // constraint 'maxSize: 5000'
        modifyDataType(tableName: 'tutorial', columnName: 'description',
                       newDataType: 'varchar(5000) not null')

        // constraint 'maxSize: 50'
        modifyDataType(tableName: 'tutorial', columnName: 'title',
                       newDataType: 'varchar(50) not null')

    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-29') {
        sql('ALTER TABLE shirooauth_identity ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-30') {
        sql('ALTER TABLE blog_entry ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-31') {
        sql('ALTER TABLE rating ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-32') {

        sql('ALTER TABLE rating_link ENGINE=InnoDB')

        // can't rename, so drop and re-create with new name
        dropIndex(tableName: 'rating_link', indexName: 'FK1827315C45884E64')
        createIndex(tableName: 'rating_link', indexName: 'FK1827315CB07DDCCB') {
            column(name: 'rating_id')
        }

        addForeignKeyConstraint(constraintName: 'FK1827315CB07DDCCB',
           baseTableName: 'rating_link', baseColumnNames: 'rating_id',
           referencedTableName: 'rating', referencedColumnNames: 'id')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-33') {
        sql('ALTER TABLE like_dislike ENGINE=InnoDB')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-34') {
        sql('ALTER TABLE bi_images ENGINE=InnoDB')
    }

    changeSet(author: 'pledbrook', id: 'UpdateTablesUTF8') {
        sql('ALTER TABLE bi_images CHARACTER SET utf8')
	sql('ALTER TABLE comment_link CHARACTER SET utf8')
	sql('ALTER TABLE DATABASECHANGELOG CHARACTER SET utf8')
	sql('ALTER TABLE DATABASECHANGELOGLOCK CHARACTER SET utf8')
	sql('ALTER TABLE download CHARACTER SET utf8')
	sql('ALTER TABLE download_file CHARACTER SET utf8')
	sql('ALTER TABLE license CHARACTER SET utf8')
	sql('ALTER TABLE like_dislike CHARACTER SET utf8')
	sql('ALTER TABLE mirror CHARACTER SET utf8')
	sql('ALTER TABLE pending_release CHARACTER SET utf8')
	sql('ALTER TABLE plugin_license CHARACTER SET utf8')
	sql('ALTER TABLE plugin_maven_repositories CHARACTER SET utf8')
	sql('ALTER TABLE plugin_release CHARACTER SET utf8')
	sql('ALTER TABLE rating CHARACTER SET utf8')
	sql('ALTER TABLE rating_link CHARACTER SET utf8')
	sql('ALTER TABLE role CHARACTER SET utf8')
	sql('ALTER TABLE role_permissions CHARACTER SET utf8')
	sql('ALTER TABLE screencast_mirror CHARACTER SET utf8')
	sql('ALTER TABLE shirooauth_identity CHARACTER SET utf8')
	sql('ALTER TABLE tag_links CHARACTER SET utf8')
	sql('ALTER TABLE tags CHARACTER SET utf8')
	sql('ALTER TABLE tutorial CHARACTER SET utf8')
	sql('ALTER TABLE user CHARACTER SET utf8')
	sql('ALTER TABLE user_info CHARACTER SET utf8')
	sql('ALTER TABLE user_permissions CHARACTER SET utf8')
	sql('ALTER TABLE user_role CHARACTER SET utf8')
	sql('ALTER TABLE version_order CHARACTER SET utf8')
	sql('ALTER TABLE video_host CHARACTER SET utf8')
	sql('ALTER TABLE web_site CHARACTER SET utf8')
	sql('ALTER TABLE web_site_bi_image CHARACTER SET utf8')
	sql('ALTER TABLE wiki_image CHARACTER SET utf8')
    }

    changeSet(author: 'burt', id: '2012-06-30-INNODB-35') {

        // making assumptions here about the FKs for these 2 join tables since they're defined by an AST

        sql('ALTER TABLE web_site_bi_image ENGINE=InnoDB')

        createIndex(tableName: 'web_site_bi_image', indexName: 'fk_web_site_bi_image_web_site') {
            column(name: 'web_site_bi_image_id')
        }
        addForeignKeyConstraint(constraintName: 'fk_web_site_bi_image_web_site',
           baseTableName: 'web_site_bi_image', baseColumnNames: 'web_site_bi_image_id',
           referencedTableName: 'web_site', referencedColumnNames: 'id')

        createIndex(tableName: 'web_site_bi_image', indexName: 'fk_web_site_bi_image_bi_images') {
            column(name: 'image_id')
        }
        addForeignKeyConstraint(constraintName: 'fk_web_site_bi_image_bi_images',
           baseTableName: 'web_site_bi_image', baseColumnNames: 'image_id',
           referencedTableName: 'bi_images', referencedColumnNames: 'id')

        sql('ALTER TABLE wiki_image_bi_image ENGINE=InnoDB')

        createIndex(tableName: 'wiki_image_bi_image', indexName: 'fk_wiki_image_bi_image_wiki_image') {
            column(name: 'wiki_image_bi_image_id')
        }
        addForeignKeyConstraint(constraintName: 'fk_wiki_image_bi_image_wiki_image',
           baseTableName: 'wiki_image_bi_image', baseColumnNames: 'wiki_image_bi_image_id',
           referencedTableName: 'wiki_image', referencedColumnNames: 'id')

        createIndex(tableName: 'wiki_image_bi_image', indexName: 'fk_wiki_image_bi_image_bi_images') {
            column(name: 'image_id')
        }
        addForeignKeyConstraint(constraintName: 'fk_wiki_image_bi_image_bi_images',
           baseTableName: 'wiki_image_bi_image', baseColumnNames: 'image_id',
           referencedTableName: 'bi_images', referencedColumnNames: 'id')
    }

    /*
    do manually:
        ALTER TABLE DATABASECHANGELOG ENGINE=InnoDB;
        ALTER TABLE DATABASECHANGELOGLOCK ENGINE=InnoDB;
    */
}
