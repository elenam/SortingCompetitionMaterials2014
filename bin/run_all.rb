
### the script for running the 2014 sorting competition 

### to pin to one core, run as: taskset -c 0 ruby run_all.rb

def medianOfThree arr
  if arr[0] <= arr[1]
    if arr[1] <= arr[2]
      arr[1]
    elsif arr[2] <= arr[0] 
      arr[0]
    else 
      arr[2]
    end 
  else 
    if arr[0] <= arr[2]
      arr[0]
    elsif arr[2] <= arr[1] 
      arr[1]
    else 
      arr[2]
    end 
  end
end

groups = 15 # number of groups (including group 0 which is the sample sorting)
loops = [20, 10, 20]
runs = 3
elements = [10000, 50000, 10000]
lambdas = [1.2] 
inFileNames = ["in1.txt", "in2.txt", "in3.txt"]
runTimes = []

# loop over lambdas and elements

lambdas.length.times do |r| 
  # generate data, store it in a file
  resultsFile = "results#{r + 1}.txt"
  system("java DataGenerator #{inFileNames[r]} #{elements[r]} #{lambdas[r]}")
  system("echo 'Running lambda =  #{lambdas[r]} with #{elements[r]} elements, #{loops[r]} loops\n' >> #{resultsFile}")
  runTimes[r] = []
 
  # run all groups
  groups.times do |j|
    system("echo 'Group#{j}\n'")
    system("echo '\n Group#{j}: \n' >> #{resultsFile}")
    runTimes[r][j] = []
    runs.times do |k|
      runTimes[r][j][k] = `java Group#{j} #{inFileNames[r]} outRun#{r + 1}Group#{j}.txt #{loops[r]}` 
      system("echo '#{runTimes[r][j][k]}' >> #{resultsFile}")
    end
    system("echo '#{medianOfThree(runTimes[r][j])}' >> #{resultsFile}")
    system("echo 'Results of diff:\n' >> #{resultsFile}")
    system("diff --ignore-all-space --brief outRun#{r + 1}Group#{j}.txt outRun#{r + 1}Group0.txt >> #{resultsFile}")
  end
end



