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
name|Author
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
comment|/**  * Will return the Authors to match the OrgSci format:  *  *<ul>  *<li>That is the first author is LastFirst, but all others are FirstLast.</li>  *<li>First names are abbreviated</li>  *<li>Spaces between abbreviated first names are NOT removed. Use  * NoSpaceBetweenAbbreviations to achieve this.</li>  *</ul>  *<p>  * See the testcase for examples.  *</p>  *<p>  * Idea from: http://stuermer.ch/blog/bibliography-reference-management-with-jabref.html  *</p>  *  */
end_comment

begin_class
DECL|class|AuthorOrgSci
specifier|public
class|class
name|AuthorOrgSci
implements|implements
name|LayoutFormatter
block|{
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
name|a
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
if|if
condition|(
name|a
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|fieldText
return|;
block|}
name|Author
name|first
init|=
name|a
operator|.
name|getAuthor
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|first
operator|.
name|getLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|a
operator|.
name|getNumberOfAuthors
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|a
operator|.
name|getAuthor
argument_list|(
name|i
argument_list|)
operator|.
name|getFirstLast
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

