begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * FullTextFinder implementation that attempts to find PDF url from a Sciencedirect article page.  */
end_comment

begin_class
DECL|class|ScienceDirectPdfDownload
specifier|public
class|class
name|ScienceDirectPdfDownload
implements|implements
name|FullTextFinder
block|{
comment|//private static final String BASE_URL = "http://www.sciencedirect.com";
annotation|@
name|Override
DECL|method|supportsSite (URL url)
specifier|public
name|boolean
name|supportsSite
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
return|return
name|url
operator|.
name|getHost
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|contains
argument_list|(
literal|"www.sciencedirect.com"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|findFullTextURL (URL url)
specifier|public
name|URL
name|findFullTextURL
parameter_list|(
name|URL
name|url
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|pageSource
init|=
name|FindFullText
operator|.
name|loadPage
argument_list|(
name|url
argument_list|)
decl_stmt|;
comment|//System.out.println(pageSource);
name|int
name|index
init|=
name|pageSource
operator|.
name|indexOf
argument_list|(
literal|"PDF ("
argument_list|)
decl_stmt|;
comment|//System.out.println(index);
if|if
condition|(
name|index
operator|>
operator|-
literal|1
condition|)
block|{
name|String
name|leading
init|=
name|pageSource
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
argument_list|)
decl_stmt|;
comment|//System.out.println(leading.toLowerCase());
name|index
operator|=
name|leading
operator|.
name|toLowerCase
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|"<a href="
argument_list|)
expr_stmt|;
comment|//System.out.println(index);
if|if
condition|(
name|index
operator|>
operator|-
literal|1
operator|&&
name|index
operator|+
literal|9
operator|<
name|leading
operator|.
name|length
argument_list|()
condition|)
block|{
name|int
name|endIndex
init|=
name|leading
operator|.
name|indexOf
argument_list|(
literal|"\""
argument_list|,
name|index
operator|+
literal|9
argument_list|)
decl_stmt|;
try|try
block|{
return|return
operator|new
name|URL
argument_list|(
comment|/*BASE_URL+*/
name|leading
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|9
argument_list|,
name|endIndex
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

