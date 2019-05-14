package kesari.autofilldemo;

import android.app.assist.AssistStructure;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.service.autofill.AutofillService;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.service.autofill.SaveCallback;
import android.service.autofill.SaveRequest;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

public class EmailAddressFiller extends AutofillService{


    void identifyEmailFields(AssistStructure.ViewNode node, List<AssistStructure.ViewNode> emailFields) {
        if(node.getClassName().contains("EditText")) {
            String viewId = node.getIdEntry();
            if(viewId!=null && (viewId.contains("email") || viewId.contains("username"))) {
                emailFields.add(node);
                return;
            }
        }
        for(int i=0; i<node.getChildCount();i++) {
            identifyEmailFields(node.getChildAt(i), emailFields);
        }
    }

    @Override
    public void onFillRequest(FillRequest request,CancellationSignal cancellationSignal,FillCallback fillCallback) {
        AssistStructure assistStructure= request.getFillContexts()
                .get(request.getFillContexts().size() - 1).getStructure();
        final Bundle bundle = request.getClientState();

        List<AssistStructure.ViewNode> emailFields = new ArrayList<>();

        identifyEmailFields(assistStructure.getWindowNodeAt(0).getRootViewNode(), emailFields);
        if(emailFields.size() == 0)
            return;

        // Load the email addresses from preferences
        SharedPreferences sharedPreferences = getSharedPreferences("EMAIL_STORAGE", MODE_PRIVATE);
        String primaryEmail = sharedPreferences.getString("PRIMARY_EMAIL", "");
        String secondaryEmail = sharedPreferences.getString("SECONDARY_EMAIL", "");

        // Create remote views for both the email addresses
        RemoteViews rvPrimaryEmail = new RemoteViews(getPackageName(), R.layout.email_suggestion);
        rvPrimaryEmail.setTextViewText(R.id.email_suggestion_item, primaryEmail);
        RemoteViews rvSecondaryEmail = new RemoteViews(getPackageName(), R.layout.email_suggestion);
        rvSecondaryEmail.setTextViewText(R.id.email_suggestion_item, secondaryEmail);

        // Choose the first email field
        AssistStructure.ViewNode emailField = emailFields.get(0);

        // Create a dataset for the email addresses
        Dataset primaryEmailDataSet = new Dataset.Builder(rvPrimaryEmail)
                .setValue(
                        emailField.getAutofillId(),
                        AutofillValue.forText(primaryEmail)
                ).build();
        Dataset secondaryEmailDataSet = new Dataset.Builder(rvSecondaryEmail)
                .setValue(
                        emailField.getAutofillId(),
                        AutofillValue.forText(secondaryEmail)
                ).build();

        // Create and send response with both datasets
        FillResponse response = new FillResponse.Builder()
                .addDataset(primaryEmailDataSet)
                .addDataset(secondaryEmailDataSet)
                .build();
        fillCallback.onSuccess(response);
    }





    @Override
    public void onSaveRequest(SaveRequest saveRequest,SaveCallback saveCallback) {

    }
}
