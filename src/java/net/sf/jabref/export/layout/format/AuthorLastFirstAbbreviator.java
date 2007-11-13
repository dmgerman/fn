begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Uses as input the fields (author or editor) in the LastFirst format.  *   * This formater enables to abbreviate the authors name in the following way:  *   * Ex: Someone, Van Something will be abbreviated as Someone, V.S.  *   * @author Carlos Silla  * @author Christopher Oezbek<oezi@oezi.de>  *   * @version 1.0 Created on 12/10/2004  * @version 1.1 Fixed bug  *          http://sourceforge.net/tracker/index.php?func=detail&aid=1466924&group_id=92314&atid=600306  */
end_comment

begin_class
DECL|class|AuthorLastFirstAbbreviator
specifier|public
class|class
name|AuthorLastFirstAbbreviator
implements|implements
name|LayoutFormatter
block|{
comment|/** 	 * @see net.sf.jabref.export.layout.LayoutFormatter#format(java.lang.String) 	 */
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
comment|/**          * This formatter is a duplicate of AuthorAbbreviator, so we simply          * call that one.          */
return|return
operator|(
operator|new
name|AuthorAbbreviator
argument_list|()
operator|)
operator|.
name|format
argument_list|(
name|fieldText
argument_list|)
return|;
block|}
block|}
end_class

end_unit

