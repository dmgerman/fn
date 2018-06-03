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
comment|/**  *<ul>  *<li>Names are given in order: first von last, jr.</li>  *<li>First names will NOT be abbreviated.</li>  *<li>Individual authors are separated by commas.</li>  *<li>There is no comma before the 'and' at the end of a list of three or more authors</li>  *</ul>  */
end_comment

begin_class
DECL|class|AuthorFirstFirstCommas
specifier|public
class|class
name|AuthorFirstFirstCommas
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
return|return
name|AuthorList
operator|.
name|fixAuthorFirstNameFirstCommas
argument_list|(
name|fieldText
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
return|;
block|}
block|}
end_class

end_unit

