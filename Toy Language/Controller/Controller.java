package Controller;

import Model.Adt.MyIStack;
import Exceptions.AdtException;
import Exceptions.MyException;

import Model.PrgState;
import Model.Value.RefValue;
import Model.Value.Value;
import Repo.Repo;
import Model.Statement.Stmt;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    Repo repo;

    ExecutorService executor;


    public Controller(Repo R) {repo = R;}

    public void addProgram(PrgState PrgS)
    {
        this.repo.addPrg(PrgS);
    }


    public Map<Integer, Value> garbageCollector(List<Integer> symTableAddr,Map<Integer,Value> heap)
    {
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues,Collection<Value> heapT){
        return Stream.concat(heapT.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();}),
                symTableValues
                .stream()
                .filter(v -> v instanceof RefValue)
                .map(v->{RefValue v1 = (RefValue) v; return v1.getAddress();})
        )
        .collect(Collectors.toList());
    }
    public void oneStepforAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prgState -> {
            try {
                repo.logPrgStateExec(prgState);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Callable<PrgState>> callableList = prgList.stream()
                .map((prgState -> (Callable<PrgState>)(prgState::oneStep)))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = executor.invokeAll(callableList).stream()
                                            .map(prgStateFuture -> {
                                                try {return prgStateFuture.get();}
                                                                    catch (InterruptedException | ExecutionException e) {e.printStackTrace();}
                                            return null;})
                                            .filter(prgState -> prgState != null)
                                            .collect(Collectors.toList());

        prgList.addAll(newPrgList);

        prgList.forEach(prgState -> {
            try {
                repo.logPrgStateExec(prgState);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        repo.setPrgList(prgList);


    }
    public void allStep() throws MyException, IOException, InterruptedException {

        executor = Executors.newFixedThreadPool(2);
        var prgList = removeCompletedPrg(repo.getPrgList());
        while(prgList.size() > 0)
        {
            repo.getPrgList().stream().forEach(prgState -> prgState.getHeapTable().setContent(garbageCollector(getAddrFromSymTable(prgState.getSymTable().getContent().values(),prgState.getHeapTable().getContent().values()),
                    prgState.getHeapTable().getContent())));
            oneStepforAllPrg(prgList);

            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
//        PrgState prg = repo.getPrgState();
//        repo.logPrgStateExec();

//        var tabel = prg.getOut();
//        System.out.println("The result of the expression is: ");
//        for(int i = 0; i < tabel.getSize() ; i++)
//        {
//            System.out.println(tabel.getElement(i));
//        }
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                        .filter(PrgState::isNotCompleted)
                        .collect(Collectors.toList());
    }
}
