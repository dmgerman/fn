begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.bibtex.comparator
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|comparator
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_comment
comment|/**  * Compares Bibtex entries based on their 'crossref' fields. Entries including  * this field are deemed smaller than entries without this field. This serves  * the purpose of always placing referenced entries after referring entries in  * the .bib file. After this criterion comes comparisons of individual fields.  */
end_comment

begin_class
DECL|class|CrossRefEntryComparator
specifier|public
class|class
name|CrossRefEntryComparator
implements|implements
name|Comparator
argument_list|<
name|BibEntry
argument_list|>
block|{
annotation|@
name|Override
DECL|method|compare (BibEntry e1, BibEntry e2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibEntry
name|e1
parameter_list|,
name|BibEntry
name|e2
parameter_list|)
block|{
name|Boolean
name|b1
init|=
name|e1
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|CROSSREF
argument_list|)
decl_stmt|;
name|Boolean
name|b2
init|=
name|e2
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|CROSSREF
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
operator|!
name|b1
operator|)
operator|&&
operator|(
operator|!
name|b2
operator|)
condition|)
block|{
return|return
literal|0
return|;
comment|// secComparator.compare(e1, e2);
block|}
if|if
condition|(
name|b1
operator|&&
name|b2
condition|)
block|{
return|return
literal|0
return|;
comment|// secComparator.compare(e1, e2);
block|}
if|if
condition|(
operator|!
name|b1
condition|)
block|{
return|return
literal|1
return|;
block|}
else|else
block|{
return|return
operator|-
literal|1
return|;
block|}
block|}
block|}
end_class

end_unit

