begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Util
import|;
end_import

begin_comment
comment|/**  * Class for putting objects of two types together.  *   * The usual way to use this is like this:  *   *<pre>  * Pair<Integer, String> intStr = new Pair<Integer, String>(5, "Hello");  *   * intStr.p == 5  * intStr.v == "Hello"  *</pre>  *   * @author oezbek  *   * @param<P> First type to be contained in the pair.  * @param<V> Second type to be contained in the pair.  */
end_comment

begin_class
DECL|class|Pair
specifier|public
class|class
name|Pair
parameter_list|<
name|P
parameter_list|,
name|V
parameter_list|>
block|{
comment|/** 	 * Direct access to the p element is allowed. 	 */
DECL|field|p
specifier|public
name|P
name|p
decl_stmt|;
comment|/** 	 * Direct access to the v element is allowed. 	 */
DECL|field|v
specifier|public
name|V
name|v
decl_stmt|;
comment|/** 	 * Constructor that sets the given p and v values. 	 *  	 * @param p first element 	 * @param v second element 	 */
DECL|method|Pair (P p, V v)
specifier|public
name|Pair
parameter_list|(
name|P
name|p
parameter_list|,
name|V
name|v
parameter_list|)
block|{
name|this
operator|.
name|p
operator|=
name|p
expr_stmt|;
name|this
operator|.
name|v
operator|=
name|v
expr_stmt|;
block|}
comment|/** 	 * Returns a comparator that compares by p. 	 *  	 *            The P type of the pair needs to be comparable. 	 * @return A comparator for the p in a pair. 	 */
DECL|method|pCompare ()
specifier|public
specifier|static
parameter_list|<
name|P
extends|extends
name|Comparable
argument_list|<
name|P
argument_list|>
parameter_list|,
name|V
parameter_list|>
name|Comparator
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
name|pCompare
parameter_list|()
block|{
return|return
operator|new
name|Comparator
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
name|arg0
parameter_list|,
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
name|arg1
parameter_list|)
block|{
return|return
name|arg0
operator|.
name|p
operator|.
name|compareTo
argument_list|(
name|arg1
operator|.
name|p
argument_list|)
return|;
block|}
block|}
return|;
block|}
comment|/** 	 * Given a comparator for p elements, returns a Comparator for pairs which 	 * uses this given comparator to make the comparison. 	 *  	 * @param comp 	 *            A comparator which will be wrapped so that it can be used to 	 *            compare 	 * @return A comparator for Pairs of type P and V which makes use of the 	 *         given comparator. 	 */
DECL|method|pCompare ( final Comparator<P> comp)
specifier|public
specifier|static
parameter_list|<
name|P
parameter_list|>
name|Comparator
argument_list|<
name|?
super|super
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
argument_list|>
name|pCompare
parameter_list|(
specifier|final
name|Comparator
argument_list|<
name|P
argument_list|>
name|comp
parameter_list|)
block|{
return|return
operator|new
name|Comparator
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
name|arg0
parameter_list|,
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
name|arg1
parameter_list|)
block|{
return|return
name|comp
operator|.
name|compare
argument_list|(
name|arg0
operator|.
name|p
argument_list|,
name|arg1
operator|.
name|p
argument_list|)
return|;
block|}
block|}
return|;
block|}
comment|/** 	 * Returns a Pair with the type and the contents of the current Pair flipped 	 * from P to V. 	 *  	 *<pre> 	 * Pair&lt;Integer, String&gt; intString = new Pair&lt;Integer, String&gt;(5,&quot;Hallo&quot;); 	 * Pair&lt;String, Integer&gt; strInteger = intString.flip(); 	 *</pre> 	 */
DECL|method|flip ()
specifier|public
name|Pair
argument_list|<
name|V
argument_list|,
name|P
argument_list|>
name|flip
parameter_list|()
block|{
return|return
operator|new
name|Pair
argument_list|<
name|V
argument_list|,
name|P
argument_list|>
argument_list|(
name|v
argument_list|,
name|p
argument_list|)
return|;
block|}
comment|/** 	 * Returns a list of pairs with P and V being switched. 	 *  	 * @param list 	 * @return 	 */
DECL|method|flipList (List<Pair<P, V>> list)
specifier|public
specifier|static
parameter_list|<
name|P
parameter_list|,
name|V
parameter_list|>
name|List
argument_list|<
name|Pair
argument_list|<
name|V
argument_list|,
name|P
argument_list|>
argument_list|>
name|flipList
parameter_list|(
name|List
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
name|list
parameter_list|)
block|{
name|LinkedList
argument_list|<
name|Pair
argument_list|<
name|V
argument_list|,
name|P
argument_list|>
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<
name|Pair
argument_list|<
name|V
argument_list|,
name|P
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
name|pair
range|:
name|list
control|)
name|result
operator|.
name|add
argument_list|(
name|pair
operator|.
name|flip
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|result
return|;
block|}
comment|/** 	 * Given a collection of Pair<P,V> it will put all V values that have the 	 * same P-value together in a set and return a collection of those sets with 	 * their associated P. 	 *  	 * Example: 	 *  	 *<pre> 	 * Collection&lt;Pair&lt;Integer, String&gt;&gt; c = new LinkedList&lt;Pair&lt;Integer, String&gt;&gt;(); 	 * c.add(new Pair&lt;Integer, String&gt;(3,&quot;Hallo&quot;)); 	 * c.add(new Pair&lt;Integer, String&gt;(4,&quot;Bye&quot;)); 	 * c.add(new Pair&lt;Integer, String&gt;(3,&quot;Adios&quot;)); 	 *  	 * Collection&lt;Pair&lt;Integer, Set&lt;String&gt;&gt;&gt; result = Pair.disjointPartition(c); 	 *  	 * result == [(3, [&quot;Hallo&quot;,&quot;Adios&quot;]), (4, [&quot;Bye&quot;])] 	 *</pre> 	 *  	 * @param list 	 * @return 	 */
DECL|method|disjointPartition ( List<Pair<P, V>> list)
specifier|public
specifier|static
parameter_list|<
name|P
extends|extends
name|Comparable
argument_list|<
name|P
argument_list|>
parameter_list|,
name|V
parameter_list|>
name|List
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|Set
argument_list|<
name|V
argument_list|>
argument_list|>
argument_list|>
name|disjointPartition
parameter_list|(
name|List
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
name|list
parameter_list|)
block|{
name|List
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|Set
argument_list|<
name|V
argument_list|>
argument_list|>
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|Set
argument_list|<
name|V
argument_list|>
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Comparator
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
name|c
init|=
name|Pair
operator|.
name|pCompare
argument_list|()
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|list
argument_list|,
name|Collections
operator|.
name|reverseOrder
argument_list|(
name|c
argument_list|)
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
name|i
init|=
name|list
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|V
argument_list|>
name|vs
decl_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
name|first
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|P
name|last
init|=
name|first
operator|.
name|p
decl_stmt|;
name|vs
operator|=
operator|new
name|HashSet
argument_list|<
name|V
argument_list|>
argument_list|()
expr_stmt|;
name|vs
operator|.
name|add
argument_list|(
name|first
operator|.
name|v
argument_list|)
expr_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
name|next
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|last
operator|.
name|compareTo
argument_list|(
name|next
operator|.
name|p
argument_list|)
operator|==
literal|0
condition|)
block|{
name|vs
operator|.
name|add
argument_list|(
name|next
operator|.
name|v
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|Pair
argument_list|<
name|P
argument_list|,
name|Set
argument_list|<
name|V
argument_list|>
argument_list|>
argument_list|(
name|last
argument_list|,
name|vs
argument_list|)
argument_list|)
expr_stmt|;
name|vs
operator|=
operator|new
name|HashSet
argument_list|<
name|V
argument_list|>
argument_list|()
expr_stmt|;
name|last
operator|=
name|next
operator|.
name|p
expr_stmt|;
name|vs
operator|.
name|add
argument_list|(
name|next
operator|.
name|v
argument_list|)
expr_stmt|;
block|}
block|}
name|result
operator|.
name|add
argument_list|(
operator|new
name|Pair
argument_list|<
name|P
argument_list|,
name|Set
argument_list|<
name|V
argument_list|>
argument_list|>
argument_list|(
name|last
argument_list|,
name|vs
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/** 	 * Returns a comparator that compares by v. 	 *  	 * @param 	 *<P> 	 * The P type is not important for this method. 	 * @param<V> 	 *            The V type of the pair needs to be comparable. 	 * @return A comparator for the v in a pair. 	 */
DECL|method|vCompare ()
specifier|public
specifier|static
parameter_list|<
name|V
extends|extends
name|Comparable
argument_list|<
name|V
argument_list|>
parameter_list|>
name|Comparator
argument_list|<
name|?
super|super
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
argument_list|>
name|vCompare
parameter_list|()
block|{
return|return
operator|new
name|Comparator
argument_list|<
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
name|arg0
parameter_list|,
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
name|arg1
parameter_list|)
block|{
return|return
name|arg0
operator|.
name|v
operator|.
name|compareTo
argument_list|(
name|arg1
operator|.
name|v
argument_list|)
return|;
block|}
block|}
empty_stmt|;
block|}
comment|/** 	 * Given a comparator for v elements, returns a Comparator for pairs which 	 * uses this given comparator to make the comparison. 	 *  	 * @param vComp 	 *            A comparator which will be wrapped so that it can be used to 	 *            compare 	 * @return A comparator for Pairs of type P and V which makes use of the 	 *         given comparator. 	 */
DECL|method|vCompare ( final Comparator<V> vComp)
specifier|public
specifier|static
parameter_list|<
name|V
parameter_list|>
name|Comparator
argument_list|<
name|?
super|super
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
argument_list|>
name|vCompare
parameter_list|(
specifier|final
name|Comparator
argument_list|<
name|V
argument_list|>
name|vComp
parameter_list|)
block|{
return|return
operator|new
name|Comparator
argument_list|<
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
argument_list|>
argument_list|()
block|{
specifier|public
name|int
name|compare
parameter_list|(
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
name|arg0
parameter_list|,
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
name|arg1
parameter_list|)
block|{
return|return
name|vComp
operator|.
name|compare
argument_list|(
name|arg0
operator|.
name|v
argument_list|,
name|arg1
operator|.
name|v
argument_list|)
return|;
block|}
block|}
empty_stmt|;
block|}
comment|/** 	 * Takes a list of P and list of V and returns a list of Pair<P,V>. If the 	 * lengths of the lists differ missing values are padded by null. 	 *  	 * @param ps 	 * @param vs 	 * @return 	 */
DECL|method|zip (List<P> ps, List<V> vs)
specifier|public
specifier|static
parameter_list|<
name|P
parameter_list|,
name|V
parameter_list|>
name|List
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
name|zip
parameter_list|(
name|List
argument_list|<
name|P
argument_list|>
name|ps
parameter_list|,
name|List
argument_list|<
name|V
argument_list|>
name|vs
parameter_list|)
block|{
name|List
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|P
argument_list|>
name|pI
init|=
name|ps
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|Iterator
argument_list|<
name|V
argument_list|>
name|vI
init|=
name|vs
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|pI
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|V
name|nextV
init|=
operator|(
name|vI
operator|.
name|hasNext
argument_list|()
condition|?
name|vI
operator|.
name|next
argument_list|()
else|:
literal|null
operator|)
decl_stmt|;
name|result
operator|.
name|add
argument_list|(
operator|new
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|(
name|pI
operator|.
name|next
argument_list|()
argument_list|,
name|nextV
argument_list|)
argument_list|)
expr_stmt|;
block|}
while|while
condition|(
name|vI
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
operator|new
name|Pair
argument_list|<
name|P
argument_list|,
name|V
argument_list|>
argument_list|(
literal|null
argument_list|,
name|vI
operator|.
name|next
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/** 	 * Unzips the given pair list by returning a list of the p values. 	 *  	 * @param list 	 * @return 	 */
DECL|method|pList (List<? extends Pair<P, ?>> list)
specifier|public
specifier|static
parameter_list|<
name|P
parameter_list|>
name|List
argument_list|<
name|P
argument_list|>
name|pList
parameter_list|(
name|List
argument_list|<
name|?
extends|extends
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
argument_list|>
name|list
parameter_list|)
block|{
name|List
argument_list|<
name|P
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<
name|P
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
name|pair
range|:
name|list
control|)
block|{
name|result
operator|.
name|add
argument_list|(
name|pair
operator|.
name|p
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/** 	 * Unzips the given pair by return a list of the v values. 	 *  	 * @param list 	 * @return 	 */
DECL|method|vList (List<? extends Pair<?, V>> list)
specifier|public
specifier|static
parameter_list|<
name|V
parameter_list|>
name|List
argument_list|<
name|V
argument_list|>
name|vList
parameter_list|(
name|List
argument_list|<
name|?
extends|extends
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
argument_list|>
name|list
parameter_list|)
block|{
name|List
argument_list|<
name|V
argument_list|>
name|result
init|=
operator|new
name|LinkedList
argument_list|<
name|V
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
name|pair
range|:
name|list
control|)
block|{
name|result
operator|.
name|add
argument_list|(
name|pair
operator|.
name|v
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
comment|/** 	 * Given an Iterator in Pair<P,V>, will return an Iterator in V that proxies all calls to the given iterator. 	 */
DECL|method|iteratorV (final Iterator<? extends Pair<?, V>> iterator)
specifier|public
specifier|static
parameter_list|<
name|V
parameter_list|>
name|Iterator
argument_list|<
name|V
argument_list|>
name|iteratorV
parameter_list|(
specifier|final
name|Iterator
argument_list|<
name|?
extends|extends
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
argument_list|>
name|iterator
parameter_list|)
block|{
return|return
operator|new
name|Iterator
argument_list|<
name|V
argument_list|>
argument_list|()
block|{
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|hasNext
argument_list|()
return|;
block|}
specifier|public
name|V
name|next
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|next
argument_list|()
operator|.
name|v
return|;
block|}
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|iterator
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
comment|/** 	 * Given an iterable in Pair<P,V> will return an iterable in P. 	 */
DECL|method|iterableV (final Iterable<? extends Pair<?, V>> iterable)
specifier|public
specifier|static
parameter_list|<
name|V
parameter_list|>
name|Iterable
argument_list|<
name|V
argument_list|>
name|iterableV
parameter_list|(
specifier|final
name|Iterable
argument_list|<
name|?
extends|extends
name|Pair
argument_list|<
name|?
argument_list|,
name|V
argument_list|>
argument_list|>
name|iterable
parameter_list|)
block|{
return|return
operator|new
name|Iterable
argument_list|<
name|V
argument_list|>
argument_list|()
block|{
specifier|public
name|Iterator
argument_list|<
name|V
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|iteratorV
argument_list|(
name|iterable
operator|.
name|iterator
argument_list|()
argument_list|)
return|;
block|}
block|}
return|;
block|}
comment|/** 	 * Given an Iterator in Pair<P,V>, will return an Iterator in P that proxies all calls to the given iterator. 	 */
DECL|method|iteratorP (final Iterator<? extends Pair<P,?>> iterator)
specifier|public
specifier|static
parameter_list|<
name|P
parameter_list|>
name|Iterator
argument_list|<
name|P
argument_list|>
name|iteratorP
parameter_list|(
specifier|final
name|Iterator
argument_list|<
name|?
extends|extends
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
argument_list|>
name|iterator
parameter_list|)
block|{
return|return
operator|new
name|Iterator
argument_list|<
name|P
argument_list|>
argument_list|()
block|{
specifier|public
name|boolean
name|hasNext
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|hasNext
argument_list|()
return|;
block|}
specifier|public
name|P
name|next
parameter_list|()
block|{
return|return
name|iterator
operator|.
name|next
argument_list|()
operator|.
name|p
return|;
block|}
specifier|public
name|void
name|remove
parameter_list|()
block|{
name|iterator
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|;
block|}
comment|/** 	 * Given an iterable in Pair<P,V> will return an iterable in P. 	 */
DECL|method|iterableP (final Iterable<? extends Pair<P,?>> iterable)
specifier|public
specifier|static
parameter_list|<
name|P
parameter_list|>
name|Iterable
argument_list|<
name|P
argument_list|>
name|iterableP
parameter_list|(
specifier|final
name|Iterable
argument_list|<
name|?
extends|extends
name|Pair
argument_list|<
name|P
argument_list|,
name|?
argument_list|>
argument_list|>
name|iterable
parameter_list|)
block|{
return|return
operator|new
name|Iterable
argument_list|<
name|P
argument_list|>
argument_list|()
block|{
specifier|public
name|Iterator
argument_list|<
name|P
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|iteratorP
argument_list|(
name|iterable
operator|.
name|iterator
argument_list|()
argument_list|)
return|;
block|}
block|}
return|;
block|}
comment|/** 	 * Debugging Output method using the toString method of P and V. 	 */
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"<"
operator|+
name|p
operator|+
literal|','
operator|+
name|v
operator|+
literal|'>'
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
operator|(
name|this
operator|.
name|p
operator|==
literal|null
condition|?
literal|0
else|:
name|this
operator|.
name|p
operator|.
name|hashCode
argument_list|()
operator|)
operator||
operator|(
name|this
operator|.
name|v
operator|==
literal|null
condition|?
literal|0
else|:
name|this
operator|.
name|v
operator|.
name|hashCode
argument_list|()
operator|)
return|;
block|}
comment|/** 	 * Returns true if both the p and v of the given pair equal p and v in the 	 * current Pair. 	 */
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|o
operator|instanceof
name|Pair
operator|)
condition|)
return|return
literal|false
return|;
name|Pair
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|other
init|=
operator|(
name|Pair
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
operator|)
name|o
decl_stmt|;
return|return
name|Util
operator|.
name|equals
argument_list|(
name|this
operator|.
name|p
argument_list|,
name|other
operator|.
name|p
argument_list|)
operator|&&
name|Util
operator|.
name|equals
argument_list|(
name|this
operator|.
name|v
argument_list|,
name|other
operator|.
name|v
argument_list|)
return|;
block|}
block|}
end_class

end_unit

