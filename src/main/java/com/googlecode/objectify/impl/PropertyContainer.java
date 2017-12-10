package com.googlecode.objectify.impl;

import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Value;
import com.googlecode.objectify.impl.translate.SaveContext;

import java.util.Map;

/**
 * In the old SDK, PropertyContainer was the superclass of Entity and EmbeddedEntity. It was a mutable property bag.
 * The new SDK has immutable BaseEntity and mutable builders. For now we'll keep the PropertyContainer abstraction,
 * but we probably want to refactor this class out.
 */
abstract public class PropertyContainer
{
	abstract public Value<?> getProperty(final String name);

	abstract public void setProperty(final String name, final Value<?> value);

	abstract public FullEntity<IncompleteKey> toFullEntity();

	abstract public boolean hasProperty(final String name);

	abstract public IncompleteKey getKey();

	abstract public Map<String, Value<?>> getProperties();

	/**
	 * Also stuffs any values in the savecontext index.
	 */
	public void setProperty(final String propertyName, final Value<?> value, final SaveContext ctx, final Path propPath) {
		this.setProperty(propertyName, value);

		if (!value.excludeFromIndexes())
			ctx.addIndex(propPath, value);
	}

}