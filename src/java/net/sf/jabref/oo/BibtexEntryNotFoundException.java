begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.oo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|oo
package|;
end_package

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: 16-Dec-2007  * Time: 10:37:23  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|BibtexEntryNotFoundException
specifier|public
class|class
name|BibtexEntryNotFoundException
extends|extends
name|Exception
block|{
DECL|field|bibtexKey
specifier|private
name|String
name|bibtexKey
decl_stmt|;
DECL|method|BibtexEntryNotFoundException (String bibtexKey, String message)
specifier|public
name|BibtexEntryNotFoundException
parameter_list|(
name|String
name|bibtexKey
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|this
operator|.
name|bibtexKey
operator|=
name|bibtexKey
expr_stmt|;
block|}
DECL|method|getBibtexKey ()
specifier|public
name|String
name|getBibtexKey
parameter_list|()
block|{
return|return
name|bibtexKey
return|;
block|}
block|}
end_class

end_unit

