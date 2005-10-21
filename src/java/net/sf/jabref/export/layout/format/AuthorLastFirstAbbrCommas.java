begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * AuthorLastFirstAbbrCommas.java  *  * Created on September 7, 2005, 1:18 PM  *  * To change this template, choose Tools | Options and locate the template under  * the Source Creation and Management node. Right-click the template and choose  * Open. You can then make changes to the template in the Source Editor.  */
end_comment

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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|ImportFormatReader
import|;
end_import

begin_comment
comment|/**  *  * @author mkovtun  */
end_comment

begin_class
DECL|class|AuthorLastFirstAbbrCommas
specifier|public
class|class
name|AuthorLastFirstAbbrCommas
implements|implements
name|LayoutFormatter
block|{
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
name|ImportFormatReader
operator|.
name|fixAuthor_lastnameFirstCommas
argument_list|(
name|fieldText
argument_list|,
literal|true
argument_list|)
return|;
block|}
block|}
end_class

end_unit

