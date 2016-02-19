

package com.mitchell.claims.entity;

import java.util.ArrayList;
import java.util.List;



public class MitchellClaimsListType {

    protected List<MitchellClaimType> claimDetails;

   
   
    
    public List<MitchellClaimType> getClaimDetails() {
    	 if (claimDetails == null) {
             claimDetails = new ArrayList<MitchellClaimType>();
         }
         return this.claimDetails;
	}

	public void setClaimDetails(List<MitchellClaimType> claimDetails) {
		this.claimDetails = claimDetails;
	}

	

}
