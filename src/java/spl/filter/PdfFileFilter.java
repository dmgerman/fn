begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|spl.filter
package|package
name|spl
operator|.
name|filter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileFilter
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: Christoph Arbeit  * Date: 08.09.2010  * Time: 15:03:36  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|PdfFileFilter
specifier|public
class|class
name|PdfFileFilter
implements|implements
name|FileFilter
block|{
DECL|method|accept (File file)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|file
parameter_list|)
block|{
name|String
name|path
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
return|return
name|isMatchingFileFilter
argument_list|(
name|path
argument_list|)
return|;
block|}
DECL|method|accept (String path)
specifier|public
name|boolean
name|accept
parameter_list|(
name|String
name|path
parameter_list|)
block|{
if|if
condition|(
name|path
operator|==
literal|null
operator|||
name|path
operator|.
name|isEmpty
argument_list|()
operator|||
operator|!
name|path
operator|.
name|contains
argument_list|(
literal|"."
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|isMatchingFileFilter
argument_list|(
name|path
argument_list|)
return|;
block|}
DECL|method|isMatchingFileFilter (String path)
specifier|private
name|boolean
name|isMatchingFileFilter
parameter_list|(
name|String
name|path
parameter_list|)
block|{
name|String
name|dateiEndung
init|=
name|path
operator|.
name|substring
argument_list|(
name|path
operator|.
name|lastIndexOf
argument_list|(
literal|"."
argument_list|)
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|dateiEndung
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"pdf"
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
end_class

end_unit

