
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

#def sortByMedians 

groups = 15 # number of groups (including group 0 which is the sample sorting)
loops = [20, 10, 20]
elements = [10000, 50000, 10000]
lambdas = [1.2, 3.2] 
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
    3.times do |k|
      runTimes[r][j][k] = (`java Group#{j} #{inFileNames[r]} outRun#{r + 1}Group#{j}.txt #{loops[r]}`).to_f
      system("echo '#{runTimes[r][j][k]}' >> #{resultsFile}")
    end
    system("echo 'Median: #{medianOfThree(runTimes[r][j])}' >> #{resultsFile}")
    runTimes[r][j][3] = medianOfThree(runTimes[r][j]) #store the median as the last element 
    runTimes[r][j][4] = j #record the group number
    system("echo 'Results of diff:\n' >> #{resultsFile}")
    system("diff --ignore-all-space --brief outRun#{r + 1}Group#{j}.txt outRun#{r + 1}Group0.txt >> #{resultsFile}")
  end
  runTimes[r][0][3] = 1000000 # a large number for group 0 (the sample solution)
  # processing the results
  system("echo 'This is my array: #{runTimes[r]}'")
  sortedTimes = (runTimes[r]).sort {|arr1, arr2| arr1[3] <=> arr2[3]}
  system("echo 'This is sorted array: #{sortedTimes}'")
end



