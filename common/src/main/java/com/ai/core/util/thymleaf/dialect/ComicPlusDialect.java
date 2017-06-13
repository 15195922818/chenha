package com.ai.core.util.thymleaf.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import com.ai.core.util.thymleaf.dialect.processor.IncludeFragmentAttrProcessor;
import com.ai.core.util.thymleaf.dialect.processor.ReplaceFragmentAttrProcessor;
import com.ai.core.util.thymleaf.dialect.processor.UseAttrProcessor;
import com.ai.core.util.thymleaf.dialect.processor.WrapAndIncludeFragmentAttrProcessor;
import com.ai.core.util.thymleaf.dialect.processor.WrapAndReplaceFragmentAttrProcessor;
import com.ai.core.util.thymleaf.dialect.processor.tag.UseResourceResolverProcessor;

public class ComicPlusDialect extends AbstractDialect {

	public static final String DialectName = "cp";
	public static final String PROCESSOR_NAME_FRAGMENT = "fragment";

	public ComicPlusDialect() {
		super();
	}

	// All of this dialect's attributes and/or tags
	// will start with 'hello:'
	@Override
	public String getPrefix() {
		return DialectName;
	}

	// The processors.
	@Override
	public Set<IProcessor> getProcessors() {
		final Set<IProcessor> processors = new HashSet<IProcessor>();

		processors.add(new UseAttrProcessor());
		processors.add(new WrapAndIncludeFragmentAttrProcessor());
		processors.add(new WrapAndReplaceFragmentAttrProcessor());
		processors.add(new IncludeFragmentAttrProcessor());
		processors.add(new ReplaceFragmentAttrProcessor());
		processors.add(new UseResourceResolverProcessor());
		return processors;
	}

}
