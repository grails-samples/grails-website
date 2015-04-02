package org.grails.ehcache;

import net.sf.ehcache.pool.Size;
import net.sf.ehcache.pool.SizeOfEngine;
import net.sf.ehcache.pool.SizeOfEngineFactory;

/**
 * Ehcache SizeOfEngineFactory and SizeOfEngine that is a No-Op implmentation
 *
 */
public class NoOpSizeOfEngineFactory implements SizeOfEngineFactory {

    @Override
    public SizeOfEngine createSizeOfEngine(int maxObjectCount, boolean abort, boolean silent) {
        return new NoOpSizeOfEngine();
    }

    public static class NoOpSizeOfEngine implements SizeOfEngine {
        @Override
        public Size sizeOf(Object key, Object value, Object container) {
            return new Size(0, false);
        }

        @Override
        public SizeOfEngine copyWith(int maxDepth, boolean abortWhenMaxDepthExceeded) {
            return new NoOpSizeOfEngine();
        }
    }
}
