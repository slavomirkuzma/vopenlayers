package org.vaadin.vol;

import java.util.HashMap;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;

/**
 * StyleMaps are collections of Styles (aka renderer intents). Styles are mapped
 * with unique style names (aka render intent). The actual style that is used to
 * render a vector depends on the state (e.g. 'selected', 'temporary') and
 * possibly overridden style name in the vector.
 * 
 */
public class StyleMap {

    private HashMap<String, Style> styles = new HashMap<String, Style>();

    /**
     * Creates a StyleMap setting the same style for all renderer intents.
     * <p>
     * From OpenLayers documentation: If just one style hash or style object is
     * passed, this will be used for all known render intents (default, select,
     * temporary)
     */
    public StyleMap(Style style) {
        setStyle(RenderIntent.DEFAULT, style);
    }

    /**
     * @param renderIntent
     * @param style
     */
    public void setStyle(String renderIntent, Style style) {
        styles.put(renderIntent, style);
    }

    /**
     * @param renderIntent
     * @param style
     */
    public void setStyle(RenderIntent renderIntent, Style style) {
        styles.put(renderIntent.getValue(), style);
    }
    
    private boolean extendDefault = false;

    /**
     * Creates a StyleMap setting with different styles for renderer intents.
     * <p>
     * Avoid passing null for one of the styles. If just want to use 2 style,
     * pass the same style for selectStyle and tempraryStyle parameters.
     * 
     * @param defaultStyle
     *            the default style to render the feature
     * @param selectStyle
     *            the style to render the feature when it is selected
     * @param temporaryStyle
     *            style to render the feature when it is temporarily selected
     */
    public StyleMap(Style defaultStyle, Style selectStyle, Style temporaryStyle) {
        setStyle(RenderIntent.DEFAULT, defaultStyle);
        if (selectStyle != null) {
            setStyle(RenderIntent.SELECT, selectStyle);
        }
        if (temporaryStyle != null) {
            setStyle(RenderIntent.TEMPORARY, temporaryStyle);
        }
    }

    public StyleMap() {
    }

    public void paint(PaintTarget target) throws PaintException {
        String[] intents = styles.keySet().toArray(
                new String[styles.size() + (extendDefault ? 1 : 0)]);
        if (extendDefault) {
            intents[intents.length - 1] = "__extendDefault__";
        }
        target.addAttribute("olStyleMap", intents);
        for (String object : intents) {
            if (!object.startsWith("__")) {
                styles.get(object).paint("olStyle_" + object, target);
            }
        }
    }

    /**
     * @param extendDefault
     *            true if other styles should extend 'default' style from this
     *            style map
     */
    public void setExtendDefault(boolean extendDefault) {
        this.extendDefault = extendDefault;
    }

    public boolean isExtendDefault() {
        return extendDefault;
    }
    
    /**
     * @param intent
     *            specifies the desired intent - usually 'default'
     * @param property
     *            specifies the property of the feature to check
     * @param simbolizer_lookup
     *            specifies the JSON object containing the key:value pairs 
     *            to use if the rule match
     * @param context
     *            optional object to check the property against. 
     *            If no context is passed in, feature attributes are used by default
     *                       
     */
    public void addUniqueValueRules(RenderIntent intent, String property, String simbolizer_lookup, Object context){
    	// TODO : not yet implemented
    }

}