begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex.comparator
package|package
name|org
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
name|java
operator|.
name|util
operator|.
name|Locale
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexString
import|;
end_import

begin_class
DECL|class|BibtexStringComparator
specifier|public
class|class
name|BibtexStringComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexString
argument_list|>
block|{
DECL|field|considerRefs
specifier|private
specifier|final
name|boolean
name|considerRefs
decl_stmt|;
comment|/**      * @param considerRefs Indicates whether the strings should be      *                     sorted according to internal references in addition to      *                     alphabetical sorting.      */
DECL|method|BibtexStringComparator (boolean considerRefs)
specifier|public
name|BibtexStringComparator
parameter_list|(
name|boolean
name|considerRefs
parameter_list|)
block|{
name|this
operator|.
name|considerRefs
operator|=
name|considerRefs
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compare (BibtexString s1, BibtexString s2)
specifier|public
name|int
name|compare
parameter_list|(
name|BibtexString
name|s1
parameter_list|,
name|BibtexString
name|s2
parameter_list|)
block|{
name|int
name|res
decl_stmt|;
comment|// First check their names:
name|String
name|name1
init|=
name|s1
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
decl_stmt|;
name|String
name|name2
init|=
name|s2
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
decl_stmt|;
name|res
operator|=
name|name1
operator|.
name|compareTo
argument_list|(
name|name2
argument_list|)
expr_stmt|;
if|if
condition|(
name|res
operator|==
literal|0
condition|)
block|{
return|return
name|res
return|;
block|}
comment|// Then, if we are supposed to, see if the ordering needs
comment|// to be changed because of one string referring to the other.x
if|if
condition|(
name|considerRefs
condition|)
block|{
comment|// First order them:
name|BibtexString
name|pre
decl_stmt|;
name|BibtexString
name|post
decl_stmt|;
if|if
condition|(
name|res
operator|<
literal|0
condition|)
block|{
name|pre
operator|=
name|s1
expr_stmt|;
name|post
operator|=
name|s2
expr_stmt|;
block|}
else|else
block|{
name|pre
operator|=
name|s2
expr_stmt|;
name|post
operator|=
name|s1
expr_stmt|;
block|}
comment|// Then see if "pre" refers to "post", which is the only
comment|// situation when we must change the ordering:
name|String
name|namePost
init|=
name|post
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
decl_stmt|;
name|String
name|textPre
init|=
name|pre
operator|.
name|getContent
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
decl_stmt|;
comment|// If that is the case, reverse the order found:
if|if
condition|(
name|textPre
operator|.
name|contains
argument_list|(
literal|"#"
operator|+
name|namePost
operator|+
literal|"#"
argument_list|)
condition|)
block|{
name|res
operator|=
operator|-
name|res
expr_stmt|;
block|}
block|}
return|return
name|res
return|;
block|}
block|}
end_class

end_unit

