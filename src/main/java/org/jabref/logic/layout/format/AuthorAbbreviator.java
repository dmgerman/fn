begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
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
name|AuthorList
import|;
end_import

begin_comment
comment|/**  * Duplicate of AuthorLastFirstAbbreviator.  *  * @see AuthorLastFirstAbbreviator  */
end_comment

begin_class
DECL|class|AuthorAbbreviator
specifier|public
class|class
name|AuthorAbbreviator
implements|implements
name|LayoutFormatter
block|{
comment|/*      * (non-Javadoc)      *      * @see org.jabref.export.layout.LayoutFormatter#format(java.lang.String)      */
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|AuthorList
name|list
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
return|return
name|list
operator|.
name|getAsLastFirstNamesWithAnd
argument_list|(
literal|true
argument_list|)
return|;
block|}
block|}
end_class

end_unit

