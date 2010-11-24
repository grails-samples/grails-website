package org.grails.tags;

/**
 * Exception thrown when a tag doesn't exist.
 *
 * @author Peter Ledbrook
 */
public class TagNotFoundException extends org.grails.taggable.TagException {
    private final String tagName;

    public TagNotFoundException(String tagName) {
        super("No tag exists with the name '" + tagName + "'.");
        this.tagName = tagName;
    }

    public String getCode() {
        return "tag.not.found";
    }

    public String getTagName() {
        return this.tagName;
    }
}
