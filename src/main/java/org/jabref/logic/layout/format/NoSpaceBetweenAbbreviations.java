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

begin_comment
comment|/**  *<p>  * LayoutFormatter that removes the space between abbreviated First names  *</p>  *<p>  * What out that this regular expression might also remove other spaces that fit  * the pattern.  *</p>  *<p>  * Example: J. R. R. Tolkien becomes J.R.R. Tolkien.  *</p>  *<p>  * See Testcase for more examples.  *<p>  */
end_comment

begin_class
DECL|class|NoSpaceBetweenAbbreviations
specifier|public
class|class
name|NoSpaceBetweenAbbreviations
implements|implements
name|LayoutFormatter
block|{
comment|/*      * Match '.' followed by spaces followed by uppercase char followed by '.'      * but don't include the last dot into the capturing group.      *      * Replace the match by removing the spaces.      *      * @see org.jabref.export.layout.LayoutFormatter#format(java.lang.String)      */
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
name|fieldText
operator|.
name|replaceAll
argument_list|(
literal|"\\.\\s+(\\p{Lu})(?=\\.)"
argument_list|,
literal|"\\.$1"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

