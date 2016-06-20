package bizapps.lv.fragmenttest;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter recyclerViewAdapter;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        for(int i=0; i<3; i++) {
            App.results.add(UUID.randomUUID().toString());
        }

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean loading = false;
            int pastVisiblesItems, visibleItemCount, totalItemCount;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();

                    if(!loading) {
                        if((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                }

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    Toast.makeText(getActivity(), "START LOADING ...", Toast.LENGTH_SHORT).show();

                                    loading = true;

                                    try {
                                        Thread.sleep(5000);
                                    }
                                    catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    finally {
                                        for(int i=0; i<10; i++) {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    App.results.add(UUID.randomUUID().toString());
                                                }
                                            });
                                        }
                                    }

                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);

                                    recyclerViewAdapter.notifyDataSetChanged();

                                    loading = false;

                                    Toast.makeText(getActivity(), "END OF LOAD", Toast.LENGTH_SHORT).show();
                                }
                            }.execute();
                        }
                    }
                }
            }
        });
    }
}

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);
        }
    }

    public RecyclerViewAdapter() {
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new RecyclerViewAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(App.results.get(position));
    }

    @Override
    public int getItemCount() {
        return App.results != null ? App.results.size() : 0;
    }
}
