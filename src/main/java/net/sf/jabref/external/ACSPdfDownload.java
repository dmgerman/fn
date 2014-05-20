begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2014 Commonwealth Scientific and Industrial Research Organisation     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
comment|/**  * FullTextFinder implementation that attempts to find PDF url from a ACS DOI.  */
end_comment

begin_class
DECL|class|ACSPdfDownload
specifier|public
class|class
name|ACSPdfDownload
implements|implements
name|FullTextFinder
block|{
DECL|field|BASE_URL
specifier|private
specifier|static
specifier|final
name|String
name|BASE_URL
init|=
literal|"http://pubs.acs.org/doi/pdf/"
decl_stmt|;
DECL|method|ACSPdfDownload ()
specifier|public
name|ACSPdfDownload
parameter_list|()
block|{      }
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
literal|"acs.org"
argument_list|)
return|;
block|}
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
try|try
block|{
return|return
operator|new
name|URL
argument_list|(
name|BASE_URL
operator|+
name|url
operator|.
name|getPath
argument_list|()
operator|.
name|substring
argument_list|(
literal|"/doi/abs/"
operator|.
name|length
argument_list|()
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
block|}
end_class

end_unit

