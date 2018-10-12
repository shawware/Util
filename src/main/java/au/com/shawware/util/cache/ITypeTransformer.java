/*
 * Copyright (C) 2018 shawware.com.au
 *
 * License: GNU General Public License V3 (or later)
 * http://www.gnu.org/copyleft/gpl.html
 */

package au.com.shawware.util.cache;

/**
 * API for transforming one type into another.
 *
 * @author <a href="mailto:david.shaw@shawware.com.au">David Shaw</a>
 * 
 * @param T1 the type to transform
 * @param T2 the resulting type
 */
@FunctionalInterface
public interface ITypeTransformer<T1, T2>
{
    T2 transform(T1 type);
}
