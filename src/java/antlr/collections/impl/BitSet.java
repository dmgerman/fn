begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.collections.impl
package|package
name|antlr
operator|.
name|collections
operator|.
name|impl
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_import
import|import
name|antlr
operator|.
name|CharFormatter
import|;
end_import

begin_comment
comment|/**A BitSet to replace java.util.BitSet.  * Primary differences are that most set operators return new sets  * as opposed to oring and anding "in place".  Further, a number of  * operations were added.  I cannot contain a BitSet because there  * is no way to access the internal bits (which I need for speed)  * and, because it is final, I cannot subclass to add functionality.  * Consider defining set degree.  Without access to the bits, I must  * call a method n times to test the ith bit...ack!  *  * Also seems like or() from util is wrong when size of incoming set is bigger  * than this.bits.length.  *  * @author Terence Parr  * @author<br><a href="mailto:pete@yamuna.demon.co.uk">Pete Wells</a>  */
end_comment

begin_class
DECL|class|BitSet
specifier|public
class|class
name|BitSet
implements|implements
name|Cloneable
block|{
DECL|field|BITS
specifier|protected
specifier|final
specifier|static
name|int
name|BITS
init|=
literal|64
decl_stmt|;
comment|// number of bits / long
DECL|field|NIBBLE
specifier|protected
specifier|final
specifier|static
name|int
name|NIBBLE
init|=
literal|4
decl_stmt|;
DECL|field|LOG_BITS
specifier|protected
specifier|final
specifier|static
name|int
name|LOG_BITS
init|=
literal|6
decl_stmt|;
comment|// 2^6 == 64
comment|/* We will often need to do a mod operator (i mod nbits).  Its      * turns out that, for powers of two, this mod operation is      * same as (i& (nbits-1)).  Since mod is slow, we use a      * precomputed mod mask to do the mod instead.      */
DECL|field|MOD_MASK
specifier|protected
specifier|final
specifier|static
name|int
name|MOD_MASK
init|=
name|BITS
operator|-
literal|1
decl_stmt|;
comment|/** The actual data bits */
DECL|field|bits
specifier|protected
name|long
name|bits
index|[]
decl_stmt|;
comment|/** Construct a bitset of size one word (64 bits) */
DECL|method|BitSet ()
specifier|public
name|BitSet
parameter_list|()
block|{
name|this
argument_list|(
name|BITS
argument_list|)
expr_stmt|;
block|}
comment|/** Construction from a static array of longs */
DECL|method|BitSet (long[] bits_)
specifier|public
name|BitSet
parameter_list|(
name|long
index|[]
name|bits_
parameter_list|)
block|{
name|bits
operator|=
name|bits_
expr_stmt|;
block|}
comment|/** Construct a bitset given the size      * @param nbits The size of the bitset in bits      */
DECL|method|BitSet (int nbits)
specifier|public
name|BitSet
parameter_list|(
name|int
name|nbits
parameter_list|)
block|{
name|bits
operator|=
operator|new
name|long
index|[
operator|(
operator|(
name|nbits
operator|-
literal|1
operator|)
operator|>>
name|LOG_BITS
operator|)
operator|+
literal|1
index|]
expr_stmt|;
block|}
comment|/** or this element into this set (grow as necessary to accommodate) */
DECL|method|add (int el)
specifier|public
name|void
name|add
parameter_list|(
name|int
name|el
parameter_list|)
block|{
comment|//System.out.println("add("+el+")");
name|int
name|n
init|=
name|wordNumber
argument_list|(
name|el
argument_list|)
decl_stmt|;
comment|//System.out.println("word number is "+n);
comment|//System.out.println("bits.length "+bits.length);
if|if
condition|(
name|n
operator|>=
name|bits
operator|.
name|length
condition|)
block|{
name|growToInclude
argument_list|(
name|el
argument_list|)
expr_stmt|;
block|}
name|bits
index|[
name|n
index|]
operator||=
name|bitMask
argument_list|(
name|el
argument_list|)
expr_stmt|;
block|}
DECL|method|and (BitSet a)
specifier|public
name|BitSet
name|and
parameter_list|(
name|BitSet
name|a
parameter_list|)
block|{
name|BitSet
name|s
init|=
operator|(
name|BitSet
operator|)
name|this
operator|.
name|clone
argument_list|()
decl_stmt|;
name|s
operator|.
name|andInPlace
argument_list|(
name|a
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
DECL|method|andInPlace (BitSet a)
specifier|public
name|void
name|andInPlace
parameter_list|(
name|BitSet
name|a
parameter_list|)
block|{
name|int
name|min
init|=
name|Math
operator|.
name|min
argument_list|(
name|bits
operator|.
name|length
argument_list|,
name|a
operator|.
name|bits
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|min
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|bits
index|[
name|i
index|]
operator|&=
name|a
operator|.
name|bits
index|[
name|i
index|]
expr_stmt|;
block|}
comment|// clear all bits in this not present in a (if this bigger than a).
for|for
control|(
name|int
name|i
init|=
name|min
init|;
name|i
operator|<
name|bits
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|bits
index|[
name|i
index|]
operator|=
literal|0
expr_stmt|;
block|}
block|}
DECL|method|bitMask (int bitNumber)
specifier|private
specifier|final
specifier|static
name|long
name|bitMask
parameter_list|(
name|int
name|bitNumber
parameter_list|)
block|{
name|int
name|bitPosition
init|=
name|bitNumber
operator|&
name|MOD_MASK
decl_stmt|;
comment|// bitNumber mod BITS
return|return
literal|1L
operator|<<
name|bitPosition
return|;
block|}
DECL|method|clear ()
specifier|public
name|void
name|clear
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
name|bits
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|bits
index|[
name|i
index|]
operator|=
literal|0
expr_stmt|;
block|}
block|}
DECL|method|clear (int el)
specifier|public
name|void
name|clear
parameter_list|(
name|int
name|el
parameter_list|)
block|{
name|int
name|n
init|=
name|wordNumber
argument_list|(
name|el
argument_list|)
decl_stmt|;
if|if
condition|(
name|n
operator|>=
name|bits
operator|.
name|length
condition|)
block|{
comment|// grow as necessary to accommodate
name|growToInclude
argument_list|(
name|el
argument_list|)
expr_stmt|;
block|}
name|bits
index|[
name|n
index|]
operator|&=
operator|~
name|bitMask
argument_list|(
name|el
argument_list|)
expr_stmt|;
block|}
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|BitSet
name|s
decl_stmt|;
try|try
block|{
name|s
operator|=
operator|(
name|BitSet
operator|)
name|super
operator|.
name|clone
argument_list|()
expr_stmt|;
name|s
operator|.
name|bits
operator|=
operator|new
name|long
index|[
name|bits
operator|.
name|length
index|]
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|bits
argument_list|,
literal|0
argument_list|,
name|s
operator|.
name|bits
argument_list|,
literal|0
argument_list|,
name|bits
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|InternalError
argument_list|()
throw|;
block|}
return|return
name|s
return|;
block|}
DECL|method|degree ()
specifier|public
name|int
name|degree
parameter_list|()
block|{
name|int
name|deg
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|bits
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|long
name|word
init|=
name|bits
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
name|word
operator|!=
literal|0L
condition|)
block|{
for|for
control|(
name|int
name|bit
init|=
name|BITS
operator|-
literal|1
init|;
name|bit
operator|>=
literal|0
condition|;
name|bit
operator|--
control|)
block|{
if|if
condition|(
operator|(
name|word
operator|&
operator|(
literal|1L
operator|<<
name|bit
operator|)
operator|)
operator|!=
literal|0
condition|)
block|{
name|deg
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|deg
return|;
block|}
comment|/** code "inherited" from java.util.BitSet */
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
operator|(
name|obj
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|obj
operator|instanceof
name|BitSet
operator|)
condition|)
block|{
name|BitSet
name|set
init|=
operator|(
name|BitSet
operator|)
name|obj
decl_stmt|;
name|int
name|n
init|=
name|Math
operator|.
name|min
argument_list|(
name|bits
operator|.
name|length
argument_list|,
name|set
operator|.
name|bits
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|n
init|;
name|i
operator|--
operator|>
literal|0
condition|;
control|)
block|{
if|if
condition|(
name|bits
index|[
name|i
index|]
operator|!=
name|set
operator|.
name|bits
index|[
name|i
index|]
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
if|if
condition|(
name|bits
operator|.
name|length
operator|>
name|n
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|bits
operator|.
name|length
init|;
name|i
operator|--
operator|>
name|n
condition|;
control|)
block|{
if|if
condition|(
name|bits
index|[
name|i
index|]
operator|!=
literal|0
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|set
operator|.
name|bits
operator|.
name|length
operator|>
name|n
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
name|set
operator|.
name|bits
operator|.
name|length
init|;
name|i
operator|--
operator|>
name|n
condition|;
control|)
block|{
if|if
condition|(
name|set
operator|.
name|bits
index|[
name|i
index|]
operator|!=
literal|0
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/** Find ranges in a set element array.  @param elems The array of      * elements representing the set, usually from Bit Set.toArray().      * @return Vector of ranges.      */
DECL|method|getRanges (int[] elems)
specifier|public
specifier|static
name|Vector
name|getRanges
parameter_list|(
name|int
index|[]
name|elems
parameter_list|)
block|{
if|if
condition|(
name|elems
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
literal|null
return|;
block|}
name|int
name|begin
init|=
name|elems
index|[
literal|0
index|]
decl_stmt|;
name|int
name|end
init|=
name|elems
index|[
name|elems
operator|.
name|length
operator|-
literal|1
index|]
decl_stmt|;
if|if
condition|(
name|elems
operator|.
name|length
operator|<=
literal|2
condition|)
block|{
comment|// Not enough elements for a range expression
return|return
literal|null
return|;
block|}
name|Vector
name|ranges
init|=
operator|new
name|Vector
argument_list|(
literal|5
argument_list|)
decl_stmt|;
comment|// look for ranges
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|elems
operator|.
name|length
operator|-
literal|2
condition|;
name|i
operator|++
control|)
block|{
name|int
name|lastInRange
decl_stmt|;
name|lastInRange
operator|=
name|elems
operator|.
name|length
operator|-
literal|1
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
name|i
operator|+
literal|1
init|;
name|j
operator|<
name|elems
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|elems
index|[
name|j
index|]
operator|!=
name|elems
index|[
name|j
operator|-
literal|1
index|]
operator|+
literal|1
condition|)
block|{
name|lastInRange
operator|=
name|j
operator|-
literal|1
expr_stmt|;
break|break;
block|}
block|}
comment|// found a range
if|if
condition|(
name|lastInRange
operator|-
name|i
operator|>
literal|2
condition|)
block|{
name|ranges
operator|.
name|appendElement
argument_list|(
operator|new
name|IntRange
argument_list|(
name|elems
index|[
name|i
index|]
argument_list|,
name|elems
index|[
name|lastInRange
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|ranges
return|;
block|}
comment|/**      * Grows the set to a larger number of bits.      * @param bit element that must fit in set      */
DECL|method|growToInclude (int bit)
specifier|public
name|void
name|growToInclude
parameter_list|(
name|int
name|bit
parameter_list|)
block|{
name|int
name|newSize
init|=
name|Math
operator|.
name|max
argument_list|(
name|bits
operator|.
name|length
operator|<<
literal|1
argument_list|,
name|numWordsToHold
argument_list|(
name|bit
argument_list|)
argument_list|)
decl_stmt|;
name|long
name|newbits
index|[]
init|=
operator|new
name|long
index|[
name|newSize
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|bits
argument_list|,
literal|0
argument_list|,
name|newbits
argument_list|,
literal|0
argument_list|,
name|bits
operator|.
name|length
argument_list|)
expr_stmt|;
name|bits
operator|=
name|newbits
expr_stmt|;
block|}
DECL|method|member (int el)
specifier|public
name|boolean
name|member
parameter_list|(
name|int
name|el
parameter_list|)
block|{
name|int
name|n
init|=
name|wordNumber
argument_list|(
name|el
argument_list|)
decl_stmt|;
if|if
condition|(
name|n
operator|>=
name|bits
operator|.
name|length
condition|)
return|return
literal|false
return|;
return|return
operator|(
name|bits
index|[
name|n
index|]
operator|&
name|bitMask
argument_list|(
name|el
argument_list|)
operator|)
operator|!=
literal|0
return|;
block|}
DECL|method|nil ()
specifier|public
name|boolean
name|nil
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
name|bits
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
if|if
condition|(
name|bits
index|[
name|i
index|]
operator|!=
literal|0
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|not ()
specifier|public
name|BitSet
name|not
parameter_list|()
block|{
name|BitSet
name|s
init|=
operator|(
name|BitSet
operator|)
name|this
operator|.
name|clone
argument_list|()
decl_stmt|;
name|s
operator|.
name|notInPlace
argument_list|()
expr_stmt|;
return|return
name|s
return|;
block|}
DECL|method|notInPlace ()
specifier|public
name|void
name|notInPlace
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
name|bits
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|bits
index|[
name|i
index|]
operator|=
operator|~
name|bits
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
comment|/** complement bits in the range 0..maxBit. */
DECL|method|notInPlace (int maxBit)
specifier|public
name|void
name|notInPlace
parameter_list|(
name|int
name|maxBit
parameter_list|)
block|{
name|notInPlace
argument_list|(
literal|0
argument_list|,
name|maxBit
argument_list|)
expr_stmt|;
block|}
comment|/** complement bits in the range minBit..maxBit.*/
DECL|method|notInPlace (int minBit, int maxBit)
specifier|public
name|void
name|notInPlace
parameter_list|(
name|int
name|minBit
parameter_list|,
name|int
name|maxBit
parameter_list|)
block|{
comment|// make sure that we have room for maxBit
name|growToInclude
argument_list|(
name|maxBit
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
name|minBit
init|;
name|i
operator|<=
name|maxBit
condition|;
name|i
operator|++
control|)
block|{
name|int
name|n
init|=
name|wordNumber
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|bits
index|[
name|n
index|]
operator|^=
name|bitMask
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|numWordsToHold (int el)
specifier|private
specifier|final
name|int
name|numWordsToHold
parameter_list|(
name|int
name|el
parameter_list|)
block|{
return|return
operator|(
name|el
operator|>>
name|LOG_BITS
operator|)
operator|+
literal|1
return|;
block|}
DECL|method|of (int el)
specifier|public
specifier|static
name|BitSet
name|of
parameter_list|(
name|int
name|el
parameter_list|)
block|{
name|BitSet
name|s
init|=
operator|new
name|BitSet
argument_list|(
name|el
operator|+
literal|1
argument_list|)
decl_stmt|;
name|s
operator|.
name|add
argument_list|(
name|el
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
comment|/** return this | a in a new set */
DECL|method|or (BitSet a)
specifier|public
name|BitSet
name|or
parameter_list|(
name|BitSet
name|a
parameter_list|)
block|{
name|BitSet
name|s
init|=
operator|(
name|BitSet
operator|)
name|this
operator|.
name|clone
argument_list|()
decl_stmt|;
name|s
operator|.
name|orInPlace
argument_list|(
name|a
argument_list|)
expr_stmt|;
return|return
name|s
return|;
block|}
DECL|method|orInPlace (BitSet a)
specifier|public
name|void
name|orInPlace
parameter_list|(
name|BitSet
name|a
parameter_list|)
block|{
comment|// If this is smaller than a, grow this first
if|if
condition|(
name|a
operator|.
name|bits
operator|.
name|length
operator|>
name|bits
operator|.
name|length
condition|)
block|{
name|setSize
argument_list|(
name|a
operator|.
name|bits
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
name|int
name|min
init|=
name|Math
operator|.
name|min
argument_list|(
name|bits
operator|.
name|length
argument_list|,
name|a
operator|.
name|bits
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|min
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|bits
index|[
name|i
index|]
operator||=
name|a
operator|.
name|bits
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
comment|// remove this element from this set
DECL|method|remove (int el)
specifier|public
name|void
name|remove
parameter_list|(
name|int
name|el
parameter_list|)
block|{
name|int
name|n
init|=
name|wordNumber
argument_list|(
name|el
argument_list|)
decl_stmt|;
if|if
condition|(
name|n
operator|>=
name|bits
operator|.
name|length
condition|)
block|{
name|growToInclude
argument_list|(
name|el
argument_list|)
expr_stmt|;
block|}
name|bits
index|[
name|n
index|]
operator|&=
operator|~
name|bitMask
argument_list|(
name|el
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets the size of a set.      * @param nwords how many words the new set should be      */
DECL|method|setSize (int nwords)
specifier|private
name|void
name|setSize
parameter_list|(
name|int
name|nwords
parameter_list|)
block|{
name|long
name|newbits
index|[]
init|=
operator|new
name|long
index|[
name|nwords
index|]
decl_stmt|;
name|int
name|n
init|=
name|Math
operator|.
name|min
argument_list|(
name|nwords
argument_list|,
name|bits
operator|.
name|length
argument_list|)
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|bits
argument_list|,
literal|0
argument_list|,
name|newbits
argument_list|,
literal|0
argument_list|,
name|n
argument_list|)
expr_stmt|;
name|bits
operator|=
name|newbits
expr_stmt|;
block|}
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|bits
operator|.
name|length
operator|<<
name|LOG_BITS
return|;
comment|// num words * bits per word
block|}
comment|/** return how much space is being used by the bits array not      *  how many actually have member bits on.      */
DECL|method|lengthInLongWords ()
specifier|public
name|int
name|lengthInLongWords
parameter_list|()
block|{
return|return
name|bits
operator|.
name|length
return|;
block|}
comment|/**Is this contained within a? */
DECL|method|subset (BitSet a)
specifier|public
name|boolean
name|subset
parameter_list|(
name|BitSet
name|a
parameter_list|)
block|{
if|if
condition|(
name|a
operator|==
literal|null
operator|||
operator|!
operator|(
name|a
operator|instanceof
name|BitSet
operator|)
condition|)
return|return
literal|false
return|;
return|return
name|this
operator|.
name|and
argument_list|(
name|a
argument_list|)
operator|.
name|equals
argument_list|(
name|this
argument_list|)
return|;
block|}
comment|/**Subtract the elements of 'a' from 'this' in-place.      * Basically, just turn off all bits of 'this' that are in 'a'.      */
DECL|method|subtractInPlace (BitSet a)
specifier|public
name|void
name|subtractInPlace
parameter_list|(
name|BitSet
name|a
parameter_list|)
block|{
if|if
condition|(
name|a
operator|==
literal|null
condition|)
return|return;
comment|// for all words of 'a', turn off corresponding bits of 'this'
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bits
operator|.
name|length
operator|&&
name|i
operator|<
name|a
operator|.
name|bits
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|bits
index|[
name|i
index|]
operator|&=
operator|~
name|a
operator|.
name|bits
index|[
name|i
index|]
expr_stmt|;
block|}
block|}
DECL|method|toArray ()
specifier|public
name|int
index|[]
name|toArray
parameter_list|()
block|{
name|int
index|[]
name|elems
init|=
operator|new
name|int
index|[
name|degree
argument_list|()
index|]
decl_stmt|;
name|int
name|en
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
operator|(
name|bits
operator|.
name|length
operator|<<
name|LOG_BITS
operator|)
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|member
argument_list|(
name|i
argument_list|)
condition|)
block|{
name|elems
index|[
name|en
operator|++
index|]
operator|=
name|i
expr_stmt|;
block|}
block|}
return|return
name|elems
return|;
block|}
DECL|method|toPackedArray ()
specifier|public
name|long
index|[]
name|toPackedArray
parameter_list|()
block|{
return|return
name|bits
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|toString
argument_list|(
literal|","
argument_list|)
return|;
block|}
comment|/** Transform a bit set into a string by formatting each element as an integer      * @separator The string to put in between elements      * @return A commma-separated list of values      */
DECL|method|toString (String separator)
specifier|public
name|String
name|toString
parameter_list|(
name|String
name|separator
parameter_list|)
block|{
name|String
name|str
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
operator|(
name|bits
operator|.
name|length
operator|<<
name|LOG_BITS
operator|)
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|member
argument_list|(
name|i
argument_list|)
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|str
operator|+=
name|separator
expr_stmt|;
block|}
name|str
operator|=
name|str
operator|+
name|i
expr_stmt|;
block|}
block|}
return|return
name|str
return|;
block|}
comment|/** Transform a bit set into a string of characters.      * @separator The string to put in between elements      * @param formatter An object implementing the CharFormatter interface.      * @return A commma-separated list of character constants.      */
DECL|method|toString (String separator, CharFormatter formatter)
specifier|public
name|String
name|toString
parameter_list|(
name|String
name|separator
parameter_list|,
name|CharFormatter
name|formatter
parameter_list|)
block|{
name|String
name|str
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
operator|(
name|bits
operator|.
name|length
operator|<<
name|LOG_BITS
operator|)
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|member
argument_list|(
name|i
argument_list|)
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|str
operator|+=
name|separator
expr_stmt|;
block|}
name|str
operator|=
name|str
operator|+
name|formatter
operator|.
name|literalChar
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|str
return|;
block|}
comment|/**Create a string representation where instead of integer elements, the      * ith element of vocabulary is displayed instead.  Vocabulary is a Vector      * of Strings.      * @separator The string to put in between elements      * @return A commma-separated list of character constants.      */
DECL|method|toString (String separator, Vector vocabulary)
specifier|public
name|String
name|toString
parameter_list|(
name|String
name|separator
parameter_list|,
name|Vector
name|vocabulary
parameter_list|)
block|{
if|if
condition|(
name|vocabulary
operator|==
literal|null
condition|)
block|{
return|return
name|toString
argument_list|(
name|separator
argument_list|)
return|;
block|}
name|String
name|str
init|=
literal|""
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
operator|(
name|bits
operator|.
name|length
operator|<<
name|LOG_BITS
operator|)
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|member
argument_list|(
name|i
argument_list|)
condition|)
block|{
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|str
operator|+=
name|separator
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|>=
name|vocabulary
operator|.
name|size
argument_list|()
condition|)
block|{
name|str
operator|+=
literal|"<bad element "
operator|+
name|i
operator|+
literal|">"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|vocabulary
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|==
literal|null
condition|)
block|{
name|str
operator|+=
literal|"<"
operator|+
name|i
operator|+
literal|">"
expr_stmt|;
block|}
else|else
block|{
name|str
operator|+=
operator|(
name|String
operator|)
name|vocabulary
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|str
return|;
block|}
comment|/**      * Dump a comma-separated list of the words making up the bit set.      * Split each 64 bit number into two more manageable 32 bit numbers.      * This generates a comma-separated list of C++-like unsigned long constants.      */
DECL|method|toStringOfHalfWords ()
specifier|public
name|String
name|toStringOfHalfWords
parameter_list|()
block|{
name|String
name|s
init|=
operator|new
name|String
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bits
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|!=
literal|0
condition|)
name|s
operator|+=
literal|", "
expr_stmt|;
name|long
name|tmp
init|=
name|bits
index|[
name|i
index|]
decl_stmt|;
name|tmp
operator|&=
literal|0xFFFFFFFFL
expr_stmt|;
name|s
operator|+=
operator|(
name|tmp
operator|+
literal|"UL"
operator|)
expr_stmt|;
name|s
operator|+=
literal|", "
expr_stmt|;
name|tmp
operator|=
name|bits
index|[
name|i
index|]
operator|>>>
literal|32
expr_stmt|;
name|tmp
operator|&=
literal|0xFFFFFFFFL
expr_stmt|;
name|s
operator|+=
operator|(
name|tmp
operator|+
literal|"UL"
operator|)
expr_stmt|;
block|}
return|return
name|s
return|;
block|}
comment|/**      * Dump a comma-separated list of the words making up the bit set.      * This generates a comma-separated list of Java-like long int constants.      */
DECL|method|toStringOfWords ()
specifier|public
name|String
name|toStringOfWords
parameter_list|()
block|{
name|String
name|s
init|=
operator|new
name|String
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bits
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|!=
literal|0
condition|)
name|s
operator|+=
literal|", "
expr_stmt|;
name|s
operator|+=
operator|(
name|bits
index|[
name|i
index|]
operator|+
literal|"L"
operator|)
expr_stmt|;
block|}
return|return
name|s
return|;
block|}
comment|/** Print out the bit set but collapse char ranges. */
DECL|method|toStringWithRanges (String separator, CharFormatter formatter)
specifier|public
name|String
name|toStringWithRanges
parameter_list|(
name|String
name|separator
parameter_list|,
name|CharFormatter
name|formatter
parameter_list|)
block|{
name|String
name|str
init|=
literal|""
decl_stmt|;
name|int
index|[]
name|elems
init|=
name|this
operator|.
name|toArray
argument_list|()
decl_stmt|;
if|if
condition|(
name|elems
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
literal|""
return|;
block|}
comment|// look for ranges
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|elems
operator|.
name|length
condition|)
block|{
name|int
name|lastInRange
decl_stmt|;
name|lastInRange
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
name|i
operator|+
literal|1
init|;
name|j
operator|<
name|elems
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
name|elems
index|[
name|j
index|]
operator|!=
name|elems
index|[
name|j
operator|-
literal|1
index|]
operator|+
literal|1
condition|)
block|{
break|break;
block|}
name|lastInRange
operator|=
name|j
expr_stmt|;
block|}
comment|// found a range
if|if
condition|(
name|str
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|str
operator|+=
name|separator
expr_stmt|;
block|}
if|if
condition|(
name|lastInRange
operator|-
name|i
operator|>=
literal|2
condition|)
block|{
name|str
operator|+=
name|formatter
operator|.
name|literalChar
argument_list|(
name|elems
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|str
operator|+=
literal|".."
expr_stmt|;
name|str
operator|+=
name|formatter
operator|.
name|literalChar
argument_list|(
name|elems
index|[
name|lastInRange
index|]
argument_list|)
expr_stmt|;
name|i
operator|=
name|lastInRange
expr_stmt|;
comment|// skip past end of range for next range
block|}
else|else
block|{
comment|// no range, just print current char and move on
name|str
operator|+=
name|formatter
operator|.
name|literalChar
argument_list|(
name|elems
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
return|return
name|str
return|;
block|}
DECL|method|wordNumber (int bit)
specifier|private
specifier|final
specifier|static
name|int
name|wordNumber
parameter_list|(
name|int
name|bit
parameter_list|)
block|{
return|return
name|bit
operator|>>
name|LOG_BITS
return|;
comment|// bit / BITS
block|}
block|}
end_class

end_unit

