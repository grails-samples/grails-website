databaseChangeLog = {
    delegate.databaseChangeLog logicalFilePath: "site-autobase"

    changeSet(id: "UpdateDescriminatorForPluginTabs", author: "pledbrook") {
        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'description-%' and class = 'org.grails.wiki.WikiPage'"
        }

        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'installation-%' and class = 'org.grails.wiki.WikiPage'"
        }

        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'faq-%' and class = 'org.grails.wiki.WikiPage'"
        }

        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'screenshots-%' and class = 'org.grails.wiki.WikiPage'"
        }

        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'plugin-%-description' and class = 'org.grails.wiki.WikiPage'"
        }

        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'plugin-%-installation' and class = 'org.grails.wiki.WikiPage'"
        }

        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'plugin-%-faq' and class = 'org.grails.wiki.WikiPage'"
        }

        update(tableName: "content") {
            column name: "class", value: "org.grails.plugin.PluginTab"
            where "title like 'plugin-%-screenshots' and class = 'org.grails.wiki.WikiPage'"
        }
    }

    changeSet(id: "IssuesUrlForPlugins", author: "pledbrook") {
        addColumn(tableName: "plugin") {
            column name: "issues_url", type: "varchar(255)", {
                constraints nullable: true
            }
        }
    }
}
