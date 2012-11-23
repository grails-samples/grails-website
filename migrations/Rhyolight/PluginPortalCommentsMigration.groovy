databaseChangeLog = {
    delegate.databaseChangeLog logicalFilePath: "site-autobase"

    changeSet(id:'PluginPortalComments', author:'Rhyolight') {
        preConditions(onFail:"MARK_RAN") {
            tableExists(tableName:'blog_entry_comment')
            columnExists(tableName:'comment', columnName:'email')
            columnExists(tableName:'comment', columnName:'poster')
        }
        dropTable(tableName:'blog_entry_comment')
        dropColumn(tableName:'comment', columnName:'email')
        dropColumn(tableName:'comment', columnName:'poster')
    }

    changeSet(id:'IncreaseCommentBodySize', author:'Rhyolight') {
        modifyDataType tableName: 'comment', columnName: 'body', newDataType: 'TEXT'
    }

    changeSet(id:'IntegrateTaggablePlugin', author:'Rhyolight') {
        preConditions(onFail:"MARK_RAN") {
            tableExists(tableName:'plugin_tags')
            tableExists(tableName:'tag')
        }
        dropTable(tableName:'plugin_tags')
        dropTable(tableName:'tag')
    }

    changeSet(id:'IntegrateCommentablePlugin', author:'Rhyolight') {
        preConditions(onFail:"MARK_RAN") {
            tableExists(tableName:'content_comment')
            tableExists(tableName:'plugin_comment')
            columnExists(tableName:'comment', columnName:'user_id')
        }
        dropTable(tableName:'content_comment')
        dropTable(tableName:'plugin_comment')
        dropColumn(tableName:'comment', columnName:'user_id')
    }

    changeSet(id:'UpdateTaggableTagLink', author:'Rhyolight') {
        preConditions(onFail:"MARK_RAN") {
            columnExists(tableName:'tag_links', columnName:'tag_class')
        }
        dropColumn(tableName:'tag_links', columnName:'tag_class')
    }

    changeSet(id:'IntegrateRateablePlugin', author:'Rhyolight') {
        preConditions(onFail:"MARK_RAN") {
            tableExists(tableName:'rating')
        }
        dropTable(tableName:'rating')
    }

    changeSet(id:'EnsuringCommentPosterIsGone', author:'Rhyolight') {
        preConditions(onFail:'MARK_RAN') {
            columnExists(tableName:'comment', columnName:'poster')
        }
        dropColumn(tableName:'comment', columnName:'poster')
    }

    changeSet(id:'EnsuringCommentEmailIsGone', author:'Rhyolight') {
        preConditions(onFail:'MARK_RAN') {
            columnExists(tableName:'comment', columnName:'email')
        }
        dropColumn(tableName:'comment', columnName:'email')
    }
}
